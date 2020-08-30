package com.xtyuns;

import net.mamoe.mirai.console.plugins.PluginBase;
import net.mamoe.mirai.message.GroupMessageEvent;
import net.mamoe.mirai.message.data.*;

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
                /*int buildId = Integer.parseInt(matcher.group(1));
                int levelId = Integer.parseInt(matcher.group(2));
                String roomId = matcher.group(3);
                Room room = new Room(buildId, levelId, roomId);
                double quantity = room.getQuantity();

                event.getGroup().sendMessage(
                        new At(event.getSender())
                                .plus(room.getName().concat("宿舍"))
                                .plus("\r\n")
                                .plus("剩余电量: ")
                                .plus(String.valueOf(quantity))
                );*/

                event.getGroup().sendMessage(
                        new At(event.getSender())
                                .plus("\r\n")
                                .plus("由于原框架项目已停止维护, 因此本功能正在进行项目迁移。")
                                .plus("\r\n\r\n")
                                .plus("项目迁移期间所有功能均暂时无法正常使用, 请等待项目迁移完成!")
                                .plus("\r\n\r\n")
                                .plus("项目地址: https://github.com/bugerx/squPower")
                                .plus("\r\n")
                                .plus("欢迎大家贡献代码~")
                );
            }

        });
    }

}          