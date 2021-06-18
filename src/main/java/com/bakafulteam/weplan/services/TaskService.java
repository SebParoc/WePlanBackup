package com.bakafulteam.weplan.services;

import com.bakafulteam.weplan.domains.*;
import com.bakafulteam.weplan.repositories.TaskRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class TaskService {
    public static List<Task> getUserTasks (TaskRepository taskRepository, User user) {
        List<SimpleTask> simpleTasks = taskRepository.findAll()
                .stream()
                .filter(task -> task.getTaskType().equals("Simple Task"))
                .map(task -> (SimpleTask)task)
                .filter(simpleTask -> simpleTask.getTaskOwner().equals(user))
                .collect(Collectors.toList());

        List<TeamsTask> teamsTasks = taskRepository.findAll()
                .stream()
                .filter(task -> task.getTaskType().equals("Teams Task"))
                .map(task -> (TeamsTask) task)
                .filter(teamsTask -> teamsTask.getCollaborators().contains(user))
                .collect(Collectors.toList());

        List<ScheduledTask> scheduledTasks = taskRepository.findAll()
                .stream()
                .filter(task -> task.getTaskType().equals("Scheduled Task"))
                .map(task -> (ScheduledTask) task)
                .filter(scheduledTask -> scheduledTask.getTaskOwner().equals(user))
                .collect(Collectors.toList());

        List<Task> userTasks = new ArrayList<>();
        userTasks.addAll(simpleTasks);
        userTasks.addAll(teamsTasks);
        userTasks.addAll(scheduledTasks);
        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("HH:mm");
        userTasks.sort(Comparator.comparing((Task task) ->
                LocalDate.parse(task.getDate(), dateformatter))
                .thenComparing((Task task) ->
                        LocalTime.parse(task.getTaskTime().substring(0, 5)
                                , timeformatter)));
        userTasks = userTasks
                .stream()
                .filter(task -> LocalDate.now().isBefore(LocalDate.parse(task.getDate(), dateformatter)))
                .collect(Collectors.toList());
        return userTasks;
    }

    public static int getWeekOfYear(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Date date = Date.from((LocalDate.parse(dateString, formatter))
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant());
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    public static String getWeekInterval(int weekOfYear) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        cal1.set(Calendar.WEEK_OF_YEAR, weekOfYear);
        cal1.set(Calendar.DAY_OF_WEEK, 2); //MONDAY
        Date startDate = cal1.getTime();
        LocalDate startLD = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String start = startLD.format(formatter);

        cal2.set(Calendar.WEEK_OF_YEAR, weekOfYear);
        cal2.set(Calendar.DAY_OF_WEEK, 1); //SUNDAY
        Date endDate = cal2.getTime();
        LocalDate endLD = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        String end = endLD.format(formatter);

        System.out.println(System.getProperty("user.home"));

        return start + " - " + end;
    }
}
