package com.xtyuns.mirai;

import com.xtyuns.mirai.wanxiao.Room;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.message.data.At;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Power extends JavaPlugin {
    public static final Power INSTANCE = new Power();

    private Power() {
        super(new JvmPluginDescriptionBuilder("com.xtyuns.mirai.power", "1.0-SNAPSHOT")
                .name("查电费")
                .info("电费查询插件")
                .author("XT.Li")
                .build());
    }

    @Override
    public void onEnable() {
        getLogger().verbose("squPower Plugin enabled!");


        GlobalEventChannel.INSTANCE.subscribeAlways(GroupMessageEvent.class, event -> {
            getLogger().info("debug: " + event.getMessage().contentToString());

            String content = event.getMessage().contentToString();

            String regx = "^查电费\\s*(\\d+)[#＃井](\\d+)(\\d\\d)";
            Pattern pattern = Pattern.compile(regx);
            Matcher matcher = pattern.matcher(content);

            if (matcher.find()) {
                int build = Integer.parseInt(matcher.group(1));
                int level = Integer.parseInt(matcher.group(2));
                String r = matcher.group(3);

                Room room;
                String msg;
                try {
                    room = new Room(build, level, r);
                    msg = room.getName() + "宿舍"
                            + "\r\n"
                            + "剩余电量: " + room.getQuantity() + "度";
                } catch (Exception e) {
                    e.printStackTrace();

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    msg = "\r\n" + dateFormat.format(new Date()) + "\r\n";
                    if (e instanceof IOException) {
                        msg += "网络通信失败, 请稍后重试!";
                    } else {
                        msg += e.getMessage();
                    }

                }

                event.getGroup().sendMessage(
                        new At(event.getSender().getId())
                                .plus(msg)
                );
            }

        });
    }
}