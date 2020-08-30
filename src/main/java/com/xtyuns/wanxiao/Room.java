package com.xtyuns.wanxiao;

public class Room {
    //8#101
    private int areaId; //2(所属区域id)
    private int buildId; //8(楼id)
    private int levelId; //1(楼层id)
    private String roomId; //01(房间序号)
    private String id; //2-2--1-8101(房间唯一标识)
    private String name; //8101(房间名称)

    private double quantity; //剩余电量

    public Room(int buildId, int levelId, String roomId) {
        this.buildId = buildId;
        this.levelId = levelId;
        this.roomId = roomId;

        this.name = buildId+""+levelId+roomId;
    }

    public String getName() {
        return name;
    }

    public double getQuantity() {
        return quantity;
    }
}
