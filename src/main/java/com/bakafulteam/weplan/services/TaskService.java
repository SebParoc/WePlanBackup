package com.bakafulteam.weplan.services;

import com.bakafulteam.weplan.domains.*;
import com.bakafulteam.weplan.repositories.TaskRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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
        return userTasks;
    }
}
