package io.github.qlain;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        int sprintDuration = -1;
        int sprintCount = -1;
        LocalDate LastSprint = null;

        try {
            sprintDuration = Integer.parseInt(args[1]);
            sprintCount = Integer.parseInt(args[2]);

            //firstSprint = LocalDate.of(2019, 8, 7);
            List<Integer> inSprint = new ArrayList<>();
            Arrays.stream(args[0].split("/")).forEach(it -> inSprint.add(Integer.parseInt(it)));
            LastSprint = LocalDate.of(inSprint.get(0), inSprint.get(1), inSprint.get(2));
        } catch (Exception e) {
            System.err.println("有効な日付と間隔、今までのスプリント回数を入力してください。\n例:2019/1/1 3 0");
            System.exit(1);
        }

        Sprint sprint = new Sprint(LastSprint, sprintDuration);
        Slack slack = new Slack(readSetting());

        while (true) {
            System.out.println("Check");
            if (sprint.isReportNow()) {
                slack.postMessage(LocalDate.now() + "\n今日は進捗報告の日です:parrot:");
                sprintCount++;
                sprint.updateLastSprint();
            }
            try {
                Thread.sleep(300_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.exit(127);
            }
        }

	    //System.out.println(sprint.isReportNow());
    }


    /**
     * SlackのIncoming Webhook URLを想定しています。
     * resources/settingファイルにhttps://hooks.slack.com/services/zzzzzzzzzzz/xxxxxxxx/yyyyyyyyyの形式でURLを記載してください。
     *
     * @return Incoming Webhook URL or Null
     */
    private static String readSetting() {
        List<String> lines;
        try {
            lines = Files.lines(Paths.get("resources/setting"), StandardCharsets.UTF_8).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return lines.get(0);
    }
}