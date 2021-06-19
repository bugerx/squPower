package com.xtyuns.mirai.wanxiao;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import static com.xtyuns.mirai.utils.Http.httpGet;


public class Room {
    private int build; //(建筑名称)
    private int level; //(楼层名称)
    private String room; //(房间序号)
    private int areaId; //(所属区域id)
    private int buildId; //(建筑id)
    private int levelId; //(楼层id)
    private String id; //2-2--1-8101(房间唯一标识, areaID-buildId--levelId-name)
    private String name; //(房间名称)


    public Room(int build, int level, String room) throws Exception {
        this.build = build;
        this.level = level;
        this.room = room;

        setAreaId();
        setBuildId();
        setLevelId();
        setIdAndName();
    }

    private void setAreaId() {
        if (build >= 11 && build <= 29) {
            this.areaId = 1;
        } else if (build >= 7 && build <= 10 || build >= 30 && build <= 31) {
            this.areaId = 2;
        } else if (build >= 1 && build <= 6) {
            this.areaId = 3;
        }
    }

    private void setBuildId() throws Exception {
        if (this.areaId < 1 || this.areaId > 3) {
            throw new Exception("未知建筑区域!(" + this.build + "号楼)");
        }
        String param = "?payProId=" + Config.payProId + "&schoolcode=" + Config.schoolcode + "&businesstype=2";
        param += "&optype=2&areaid=" + this.areaId + "&buildid=0&unitid=0&levelid=0";

        String data;
        data = httpGet(Config.HOST + Config.ROOMPATH + param);

        JsonObject info = JsonParser.parseString(data).getAsJsonObject();
        if (null == info) {
            throw new Exception("获取建筑标识失败, 请稍后重试!");
        } else if (!"获取房间信息成功".equals(info.get("msg").getAsString())) {
            throw new Exception(info.get("msg").getAsString());
        }
        JsonArray rooms = info.get("roomlist").getAsJsonArray();
        for (Object o : rooms) {
            JsonObject room = (JsonObject) o;
            if ((this.build + "号楼").equals(room.get("name").getAsString()) || ("#" + this.build + "公寓").equals(room.get("name").getAsString())) {
                this.buildId = room.get("id").getAsInt();
                return;
            }
        }

        throw new Exception("未找到指定建筑!(" + this.build + "号楼)");
    }

    private void setLevelId() throws Exception {
        if (0 == this.buildId) {
            setBuildId();
        }
        String param = "?payProId=" + Config.payProId + "&schoolcode=" + Config.schoolcode + "&businesstype=2";
        param += "&optype=3&areaid=" + this.areaId + "&buildid=" + this.buildId + "&unitid=0&levelid=0";

        String data;
        data = httpGet(Config.HOST + Config.ROOMPATH + param);

        JsonObject info = JsonParser.parseString(data).getAsJsonObject();
        if (null == info) {
            throw new Exception("获取楼层标识失败, 请稍后重试!");
        } else if (!"获取房间信息成功".equals(info.get("msg").getAsString())) {
            throw new Exception(info.get("msg").getAsString());
        }

        JsonArray rooms = info.get("roomlist").getAsJsonArray();
        for (Object o : rooms) {
            JsonObject room = (JsonObject) o;
            if ((this.level + "").equals(room.get("name").getAsString()) || (this.level + "层").equals(room.get("name").getAsString()) || (this.level - 1 + "、" + this.level + "层").equals(room.get("name").getAsString()) || (this.level + "、" + (this.level + 1) + "层").equals(room.get("name").getAsString())) {
                this.levelId = room.get("id").getAsInt();
                return;
            }
        }

        throw new Exception("未找到指定楼层!(" + this.build + "#" + this.level + this.room + ")");
    }

    private void setIdAndName() throws Exception {
        if (0 == this.levelId) {
            setLevelId();
        }
        String param = "?payProId=" + Config.payProId + "&schoolcode=" + Config.schoolcode + "&businesstype=2";
        param += "&optype=4&areaid=" + this.areaId + "&buildid=" + this.buildId + "&unitid=0&levelid=" + this.levelId;

        String data;
        data = httpGet(Config.HOST + Config.ROOMPATH + param);

        JsonObject info = JsonParser.parseString(data).getAsJsonObject();
        if (null == info) {
            throw new Exception("获取房间标识失败, 请稍后重试!");
        } else if (!"获取房间信息成功".equals(info.get("msg").getAsString())) {
            throw new Exception(info.get("msg").getAsString());
        }

        JsonArray rooms = info.get("roomlist").getAsJsonArray();
        for (Object o : rooms) {
            JsonObject room = (JsonObject) o;
            if ((this.build + "" + this.level + this.room).equals(room.get("name").getAsString()) || (this.level + this.room).equals(room.get("name").getAsString())) {
                this.id = room.get("id").getAsString();
                this.name = room.get("name").getAsString();
                return;
            }
        }

        throw new Exception("未找到指定房间!(" + this.build + "#" + this.level + this.room + ")");
    }

    public double getQuantity() throws Exception {
        if (null == this.id) {
            setIdAndName();
        }
        String param = "?payProId=" + Config.payProId + "&schoolcode=" + Config.schoolcode + "&businesstype=2";
        param += "&roomverify=" + this.id;

        String data;
        data = httpGet(Config.HOST + Config.GETROOMSTATEPATH + param);

        JsonObject info = JsonParser.parseString(data).getAsJsonObject();
        if (null == info || "null".equals(info.get("returnmsg").toString())) {
            throw new Exception("获取房间剩余电量失败, 请稍后重试!");
        } else if (!"SUCCESS".equals(info.get("returnmsg").getAsString())) {
            throw new Exception(info.get("returnmsg").getAsString());
        }

        return info.get("quantity").getAsDouble();
    }

    public String getName() {
        return name;
    }

}