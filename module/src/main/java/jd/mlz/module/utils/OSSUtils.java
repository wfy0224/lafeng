package jd.mlz.module.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author wangfeiyu
 * * @date 2024-12-02
 */

@Slf4j
public class OSSUtils {

    /**
     * @param inputStream 上传文件的输入流
     * @param fileName    文件名：uuid_wxh.jpg
     * @return url/"" 文件所在网络地址
     */
    public static String uploadImageToAliYunOSS(InputStream inputStream, String fileName) {

        String OBJECT_NAME = SpringUtils.getProperty("OBJECT_NAME");
        String ENDPOINT = SpringUtils.getProperty("ENDPOINT");
        String BUCKET_NAME = SpringUtils.getProperty("BUCKET_NAME");
        String ACCESS_KEY_ID = SpringUtils.getProperty("ACCESS_KEY_ID");
        String ACCESS_KEY_SECRET = SpringUtils.getProperty("ACCESS_KEY_SECRET");
        String url = "";
        //key:  video_project/image/yymm/dd/uuid_wxh.jpg
        String datePath = new SimpleDateFormat("yyMM/dd/").format(new Date());
        String key;
        key = OBJECT_NAME + "image/" + datePath + fileName;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);

        ObjectMetadata meta = new ObjectMetadata();

        // 创建PutObjectRequest对象。
        PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, key, inputStream,meta);

        ossClient.putObject(putObjectRequest);
        ossClient.shutdown();
        try {
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //获取url

        url = "https://" + BUCKET_NAME + "." + ENDPOINT + "/" + key;
        return url;
    }

    public static byte[] getAllBytes(InputStream inputStream) throws IOException{
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        return buffer.toByteArray();
    }

    /**
     * 将对象列表生成Excel流并上传到OSS
     *
     * @param dataList      数据列表
     * @param clazz         Excel对应的Java类
     * @param fileName OSS文件名
     * @param <T>           泛型类型
     * @throws IOException 上传失败时抛出异常
     */
    public static <T> String uploadExcelStreamToOSS(List<T> dataList, Class<T> clazz, String fileName) throws IOException {
        String OBJECT_NAME = SpringUtils.getProperty("OBJECT_NAME");
        String ENDPOINT = SpringUtils.getProperty("ENDPOINT");
        String BUCKET_NAME = SpringUtils.getProperty("BUCKET_NAME");
        String ACCESS_KEY_ID = SpringUtils.getProperty("ACCESS_KEY_ID");
        String ACCESS_KEY_SECRET = SpringUtils.getProperty("ACCESS_KEY_SECRET");
        //key:  video_project/image/yymm/dd/uuid_wxh.jpg
        String datePath = new SimpleDateFormat("yyMM/dd/").format(new Date());
        String key;
        key = OBJECT_NAME + "file/" + datePath + fileName;
        // 生成Excel流
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        EasyExcel.write(outputStream, clazz)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("Sheet1")
                .doWrite(dataList);

        // 将流转换为输入流
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());

        // 初始化OSS客户端
        OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);

        try {
            // 创建上传请求
            PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, key, inputStream);

            // 上传文件流
            ossClient.putObject(putObjectRequest);

            log.info("Excel流上传成功: " + key);
            return key+"$"+"https://" + BUCKET_NAME + "." + ENDPOINT + "/" + key;
        } catch (Exception e) {
            System.err.println("Excel流上传失败: " + e.getMessage());
            throw new IOException("上传Excel流到OSS失败", e);
        } finally {
            // 关闭OSS客户端
            if (ossClient != null) {
                ossClient.shutdown();
            }
            // 关闭流
            inputStream.close();
            outputStream.close();
        }
    }
}
