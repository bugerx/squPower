package com.xtyuns;

import com.xtyuns.wanxiao.Room;
import net.mamoe.mirai.console.plugins.PluginBase;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.contact.Member;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class PluginMain extends PluginBase {

    public void onLoad() {
        getLogger().info("squPower Plugin loaded!");
    }

    public void onEnable() {
        getLogger().info("squPower Plugin enabled!");

        this.getEventListener().subscribeAlways(GroupMessageEvent.class, (GroupMessageEvent event) -> {
            String content = event.getMessage().contentToString();

            String regx = "^查电费\\s*(\\d+)[#＃井](\\d+)(\\d\\d)";
            Pattern pattern = Pattern.compile(regx);
            Matcher matcher = pattern.matcher(content);

            if (matcher.find()) {
                int build = Integer.parseInt(matcher.group(1));
                int levelId = Integer.parseInt(matcher.group(2));
                String r = matcher.group(3);

                Room room;
                String msg;
                try {
                    room = new Room(build, levelId, r);
                    msg = room.getName() + "宿舍"
                            + "\r\n"
                            + "剩余电量: " + room.getQuantity();
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
                        new At(event.getSender())
                                .plus(msg)
                );

                /*event.getGroup().sendMessage(
                        new At(event.getSender())
                                .plus("\r\n")
                                .plus("由于原框架项目已停止维护, 因此本功能正在进行项目迁移。")
                                .plus("\r\n\r\n")
                                .plus("项目迁移期间所有功能均暂时无法正常使用, 请等待项目迁移完成!")
                                .plus("\r\n\r\n")
                                .plus("项目地址: https://github.com/bugerx/squPower")
                                .plus("\r\n")
                                .plus("欢迎大家贡献代码~")
                );*/
            }

        });
    }

}          