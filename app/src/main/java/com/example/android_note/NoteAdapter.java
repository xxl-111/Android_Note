package com.example.android_note;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.PreferenceChangeEvent;

public class NoteAdapter extends BaseAdapter implements Filterable {

    private Context mContext;

    private List<Notes> backList;//备份原始数据
    private List<Notes> noteList;//这个数据是会改变的，所以要有个变量来备份一下原始数据
    private MyFilter myFilter;

    public NoteAdapter(Context mContext,List<Notes> noteList){
        //构造函数，将数据收录到backList中，数据包括内容和正在改变的notes即noteList集合
        this.mContext=mContext;
        this.noteList=noteList;
        backList=noteList;
    }

    @Override//必须重写的函数，获取笔记数目
    public int getCount(){
        return noteList.size();
    }
    @Override//必须重写的函数，获取在屏幕中的位置
    public Object getItem(int position){
        return noteList.get(position);
    }
    @Override//必须重写的函数，获取在屏幕中的位置
    public long getItemId(int position){
        return position;
    }
    @Override//必须重写的函数，获取View
    public View getView(int position, View convertView, ViewGroup parent){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(mContext);
        mContext.setTheme(R.style.DayTheme);//设置Context的主体风格
        @SuppressLint("ViewHolder")
        View v=View.inflate(mContext,R.layout.note_layout,null);//初始化View，需要用到inflate，对其设置布局格式
        TextView tvContent=(TextView)v.findViewById(R.id.tv_content);//TextView类型，用来锁定res目录下的tv_content，找到其设置格式
        TextView tv_time=(TextView)v.findViewById(R.id.tv_time);//TextView类型，用来锁定time格式
        String allText=noteList.get(position).getContent();//初始化allText，目的用来获取所有的笔记数目

        tvContent.setText(allText);//将获取的字符串集合应用到内容栏中
        tv_time.setText(noteList.get(position).getTime());//将获取的时间字符串应用到时间栏中
        //save note id to tag
        v.setTag(noteList.get(position).getTag());//设置笔记类型
        return v;//以View类型返回
    }
    @Override//必须重写的函数，文件过滤器，筛选所需内容并且能够排序
    public Filter getFilter(){//该函数目的获取实例化对象的过滤器对象
        if(myFilter==null){
            myFilter = new MyFilter();
        }
        return myFilter;
    }

    class MyFilter extends Filter {//内部类的建立，目的是为了实现过滤器的逻辑层
        @Override
        protected FilterResults performFiltering(CharSequence charSequence){
            FilterResults results=new FilterResults();
            List<Notes> list;
            if(TextUtils.isEmpty(charSequence)){
                list=backList;
            }else{
                list=new ArrayList<>();
                for(Notes note:backList){
                    if(note.getContent().contains(charSequence)){
                        list.add(note);
                    }
                }
            }//上面的判断语句实现，根据判断笔记内容是否为空，最终为了根据字符序列，将note对象插入到笔记序列中
            results.values=list;//更新序列的内容
            results.count=list.size();//更新序列的大小

            return results;//返回新的序列，即所要的（已经过滤的）笔记内容
        }

        @Override//必须重写的方法，用于当Adapter的数据发生变化时，通知UI主线程根据新的数据绘制界面
        protected void publishResults(CharSequence charSequence,FilterResults filterResults){
            noteList=(List<Notes>)filterResults.values;
            if(filterResults.count>0){
                notifyDataSetChanged();
            }else{
                notifyDataSetInvalidated();
            }
        }
    }
}
