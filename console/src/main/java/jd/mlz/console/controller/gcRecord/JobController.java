package jd.mlz.console.controller.gcRecord;


import jd.mlz.console.domain.PagingVO;
import jd.mlz.module.module.Job.dto.GroupDTO;
import jd.mlz.module.module.Job.dto.JobDTO;
import jd.mlz.module.module.Job.dto.JobHistoryDTO;
import jd.mlz.module.module.Job.service.JobService;
import jd.mlz.module.utils.Response;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author wangfeiyu
 * * @date 2025-03-14
 */

@RestController
public class JobController {
    @Autowired
    private JobService jobService;

    @RequestMapping("/api/jobs/all")
    public Response getAllJobs(@RequestParam("page")Integer page, @RequestParam("pageSize")Integer pageSize) throws SchedulerException {
        List<JobDTO> allJobs = jobService.getAllJobs();
        page = page == null || page < 1 ? 1 : page;
        int total = allJobs.size();
        int start = (page - 1) * pageSize;
        int end = Math.min(start + pageSize, allJobs.size());
        PagingVO pagingVO = new PagingVO();
        pagingVO.setList(allJobs.subList(start,end));
        pagingVO.setPage(page);
        pagingVO.setTotal(total);
        return new Response(1001,pagingVO);
    }

    @RequestMapping("/api/jobs/create")
    public Response createJob(@RequestParam String jobClass,  @RequestParam String jobGroup, @RequestParam String jobName, @RequestParam String description, @RequestParam String cronExpression) {
        if(jobService.createJob(jobClass, jobName, jobGroup, description, cronExpression)){
            return new Response(3001);
        }
        return new Response(3002);
    }

    @RequestMapping("/api/jobs/update")
    public Response updateJob(@RequestParam String jobGroup, @RequestParam String jobName, @RequestParam String description, @RequestParam String cronExpression) {
        if(jobService.updateJob(jobGroup, jobName, description,cronExpression)){
            return new Response(3005);
        }
        return new Response(3006);
    }

    @RequestMapping("/api/jobs/delete")
    public Response deleteJob(@RequestParam String jobGroup, @RequestParam String jobName) {
        if(jobService.deleteJob(jobGroup, jobName)){
            return new Response(3003);
        }
        return new Response(3004);
    }

    @RequestMapping("/api/jobs/pause")
    public Response pauseJob(@RequestParam String jobGroup, @RequestParam String jobName) {
        if(jobService.pauseJob(jobGroup, jobName)){
            return new Response(3007);
        }
        return new Response(3008);
    }

    @RequestMapping("/api/jobs/resume")
    public Response resumeJob(@RequestParam String jobGroup, @RequestParam String jobName) {
        if(jobService.resumeJob(jobGroup, jobName)){
            return new Response(3009);
        }
        return new Response(3010);
    }

    @RequestMapping("/api/jobs/trigger")
    public Response triggerJob(@RequestParam String jobGroup, @RequestParam String jobName) {
        if(jobService.triggerJob(jobGroup, jobName)){
            return new Response(3011);
        }
        return new Response(3012);
    }

    @RequestMapping("/api/job_groups/all")
    public Response getAllGroups() throws SchedulerException {
        List<GroupDTO> allGroups = jobService.getAllGroups();
        PagingVO pagingVO = new PagingVO();
        pagingVO.setList(allGroups);
        pagingVO.setPage(1);
        pagingVO.setTotal(allGroups.size());
        return new Response(1001,pagingVO);
    }

}
