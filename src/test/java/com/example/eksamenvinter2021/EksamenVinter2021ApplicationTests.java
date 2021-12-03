package com.example.eksamenvinter2021;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@SpringBootTest
class EksamenVinter2021ApplicationTests {

    @Test
    void contextLoads() {
        daysBetween("2021-11-29", "2021-12-01");//MySQL gemmer datoer i formatet YYYY-MM-DD
    }

    //Find project_end dato:
    //SELECT min(startDate) FROM projects WHERE project_id = X
    //SELECT max(endDate) FROM subtasks WHERE project_id = X
    /*select max(deadline) from subtask
    join task using (task_id)
    join subproject using (subproject_id)
    join project using (project_id)
    where project_id = ?;
    */
    public static long daysBetween(String input1, String input2) {
        //Find dato fra String
        LocalDate date1 = LocalDate.parse(input1);
        LocalDate date2 = LocalDate.parse(input2);

        //Udregning dage mellem to datoer
        long noOfDaysBetween = ChronoUnit.DAYS.between(date1, date2);

        System.out.println(noOfDaysBetween);
        return noOfDaysBetween;
        //TODO: Hente en dato fra databasen at regne fra (projekt/opgave start- og slutdato)
    }
}
