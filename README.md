# BaseAdapter
Android RecyclerView的万能Adapter。支持多类型Item，支持设置Header、Footer、 列表为空时展示布局、加载更多布局、常用动画等，支持RecyclerView的更新、插入、删除，以及列表点击监听、列表长按监听等常用功能。

A common base adapter project for RecyclerView in Android.It support using multi-type item, and setting header, footer, emptyview, loading more, anim,update list,click listener, long click listener and any others in common use for RecyclerView or its adapter.

[下载示例APK体验](https://github.com/HuangGangHust/BaseAdapter/raw/master/BaseAdapterSample-debug.apk)



## 依赖

1. 在项目的根目录的build.gradle文件中（注意：不是module的build.gradle文件）加入如下依赖：

   ```
   allprojects {
   	repositories {
   		...
   		maven { url "https://jitpack.io" }
   	}
   }
   ```


1. 在module的build.gradle文件中加入如下依赖：

   ```
   dependencies {
       compile 'com.github.HuangGangHust:BaseAdapter:1.0.0'
   }
   ```



## 功能

1. 一键生成Adapter，只需书写业务代码即可；
2. 省去列表项处理中大量实例化组件的工作，使用viewHolder.setText(viewId, text)、setOnClickListener(viewId, listener)等方法即可一键完成所需，且可自行扩展；
3. 一行代码完成Header、Footer、 列表为空时展示布局、加载更多布局等的设置；
4. 支持RecyclerView的更新、插入、删除，以及列表点击监听、列表长按监听等常用处理。



## 使用

### 1、单类型Item的Adapter

```java
public class ProjectRecyclerAdapter extends BaseSingleItemAdapter<Project> {
    public ProjectRecyclerAdapter(RecyclerView recyclerView, List datas) {
        super(recyclerView, datas, R.layout.item_example);
    }

    @Override
    public void onBind(BaseViewHolder viewHolder, Project itemData, int position, int scrollState) {
        viewHolder.setText(R.id.tv_title, itemData.getProjectName());
        viewHolder.setText(R.id.tv_content, itemData.getUpdateTime());
    }
}
```



### 2、多类型Item

对于多中itemviewtype的处理参考：https://github.com/sockeqwe/AdapterDelegates ，具有极高的扩展性。

``` java
BaseRecyclerAdapter multiItemAdapter = new BaseRecyclerAdapter<>(recyclerView, projects).addItemSupport(new ExampleItemSupport())// 设置类型1列表项
        .addItemSupport(new ProjectItemSupport());// 设置类型2列表项
```

每种Item类型对应一个ItemSupport，eg：

```java 
public class ExampleItemSupport extends BaseItemSupport<Project> {
    public ExampleItemSupport() {
        super(R.layout.item_example);
    }

    @Override
    public boolean isMatched(Project itemData, int position) {
        return position % 2 == 0;
    }

    @Override
    public void onBind(BaseViewHolder viewHolder, Project itemData, int position, int scrollState) {
        viewHolder.setText(R.id.tv_title, itemData.getProjectName());
        viewHolder.setText(R.id.tv_content, itemData.getUpdateTime());
    }
}
```



### 3、设置EmptyView（类似ListView设置EmptyView）





## 欢迎Star，欢迎反馈问题：huangganghust@qq.com



## 感谢

