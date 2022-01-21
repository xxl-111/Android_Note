package com.example.android_note;
//
//该类表示笔记类，每一条笔记为该类的实例化对象
//id表示从0自增的笔记id
//content表示String类型的笔记内容
//time表示格式化获取当前最新时间，即最新修改笔记的时间
//tag表示类别，笔记的分类，在改进优化中可以用到
//重写toString方法意图格式化将要输入到在笔记栏中的表示内容
//
public class Notes {
    private long id;
    private String content;
    private String time;
    private String title;
    private int tag;//分类或者优先级设置



    public Notes(long id, String content, String time, String title, int tag) {
        this.id = id;
        this.content = content;
        this.time = time;
        this.title = title;
        this.tag = tag;
    }



    public Notes(){}


    //get
    public long getId() { return id; }
    public String getContent() { return content; }
    public String getTime() { return time; }
    public String getTitle() { return title; }
    public int getTag() { return tag; }
    //set
    public void setId(long id){this.id=id;}
    public void setContent(String content){this.content=content;}
    public void setTime(String time){this.time=time;}
    public void setTitle(String title) { this.title = title; }
    public void setTag(int tag){this.tag=tag;}
    //toString()
    @Override
    public String toString(){
        return content+"\n"+time.substring(5,16)+" "+id;//for debug
    }

}
