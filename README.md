# BaseAdapter
Android RecyclerView的万能Adapter。支持多类型Item，支持设置Header、Footer、 列表为空时展示布局、加载更多布局、常用动画等，支持RecyclerView的更新、插入、删除，以及列表点击监听、列表长按监听等常用功能。

A common base adapter project for RecyclerView in Android.It support using multi-type item, and setting header, footer, emptyview, loading more, anim,update list,click listener, long click listener and any others in common use for RecyclerView or its adapter.

![](https://jitpack.io/v/HuangGangHust/SlideDrawerHelper.svg)](https://jitpack.io/#HuangGangHust/BaseAdapter)

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
        // 直接传入该Item的layout即可
        super(recyclerView, datas, R.layout.item_example);
    }

    @Override
    public void onBind(BaseViewHolder viewHolder, Project itemData, int position, int scrollState) {
        // 在此处直接写业务逻辑。无需编写实例化代码，一行代码搞定各种设置。
        viewHolder.setText(R.id.tv_title, itemData.getProjectName());
        viewHolder.setText(R.id.tv_content, itemData.getUpdateTime());
    }
}
```

```java
mRecyclerView.setAdapter(new ProjectRecyclerAdapter(mRecyclerView, mDatas));
```



### 2、多类型Item

对于多中itemviewtype的处理参考：https://github.com/sockeqwe/AdapterDelegates ，具有极高的扩展性。

``` java
BaseRecyclerAdapter multiItemAdapter = new BaseRecyclerAdapter<>(recyclerView, projects).addItemSupport(new ExampleItemSupport())// 设置类型1列表项
         .addItemSupport(new ProjectItemSupport());// 设置类型2列表项
```

```
mRecyclerView.setAdapter(multiItemAdapter);
```



**说明**：每种Item类型对应一个ItemSupport，eg：

```java 
public class ExampleItemSupport extends BaseItemSupport<Project> {
    public ExampleItemSupport() {
        super(R.layout.item_example);
    }

    @Override
    public boolean isMatched(Project itemData, int position) {
        // 匹配使用该类型Item的条件，return true时表示使用该类型Item
        return position % 2 == 0;
    }

    @Override
    public void onBind(BaseViewHolder viewHolder, Project itemData, int position, int scrollState) {
        viewHolder.setText(R.id.tv_title, itemData.getProjectName());
        viewHolder.setText(R.id.tv_content, itemData.getUpdateTime());
    }
}
```



### 3、设置EmptyView、HeaderView、FooterView

直接调用 `mAdapter.setEmptyView(view)` 或 `mAdapter.setHeaderView(view)` 或  `mAdapter.setFooterView(view)` 将对应的实例作为参数传入即可。且支持链式设置。

```java
BaseRecyclerAdapter multiItemAdapter = new BaseRecyclerAdapter<>(recyclerView, projects)
                .addItemSupport(new ExampleItemSupport())// 设置类型1列表项
                .addItemSupport(new ProjectItemSupport())// 设置类型2列表项
                .setEmptyView(emptyView)// 设置列表为空时展示
                .setHeaderView(headerView)// 设置列表的头部
                .setFooterView(footerView);// 设置列表的尾部
```

```java
mRecyclerView.setAdapter(multiItemAdapter);
```

**说明**：若调用 `mAdapter.setEmptyView(null)` 或 `mAdapter.setHeaderView(null)` 或  `mAdapter.setFooterView(null)`  时，表示清除相应组件。



### 4、设置加载更多

1. 调用 `mAdapter.setFooterView(view)` 将加载更多作为footerView进行设置；
2. 通过 `setFooterViewOnBindListener(footerViewOnBindListener)` 设置RecyclerView在页面中加载该布局时的回调监听，并在 `BaseRecyclerAdapter.FooterViewOnBindListener` 的 `onBind()` 方法中处理加载更多的业务。

```java
// RecyclerView在页面中加载footerView时回调监听
mAdapter.setFooterViewOnBindListener(new BaseRecyclerAdapter.FooterViewOnBindListener() {
    @Override
    public void onBind() {// 加载更多的回调
        onLoadMore();// 加载更多的业务处理方法
    }
});
```

**说明**：

1. 若调用  `mAdapter.setFooterView(null)`  时，表示清除相应组件；
2. 若同时存在固定展示的footerView和加载更多布局，可将其合并至一个layout中，作为一个footerView传入，并控制加载更多的业务。（如果存在过于庞大或复杂的footerView，建议找相关负责人进行页面优化，你们该提升自己产品的逼格和友好性了）



### 5、设置列表项点击/长按监听

```java
// 列表项点击监听
mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
    @Override
    public void onItemClick(View itemView, RecyclerView.ViewHolder holder, int position) {
        // 业务处理
    }
});

// 列表项长按监听
mAdapter.setOnItemLongClickListener(new BaseRecyclerAdapter.OnItemLongClickListener() {
    @Override
    public boolean onItemLongClick(View itemView, RecyclerView.ViewHolder holder, int position) {
        // 业务处理
    }
});
```



### 6、列表/网格处理：更新、插入、删除等

分别调用 `BaseRecyclerAdapter` 或其子类的 `updateData(datas)`、`insertData(position, data)` 和 `removeData(position)` 进行列表/网格的更新、插入和删除操作。若有设置RecyclerView动画，则会有相应动画展示。



### 7、其他可扩展使用的功能

```java
/**
* 初始化方法。可复写BaseRecyclerAdapter中的此方法，实现Adapter初始化时所需其他特殊操作。
*
* @param viewHolder  通用适配器
* @param convertView Item布局
*/
public void onCreate(BaseViewHolder viewHolder, View convertView) {
}
```



## 欢迎Star，欢迎反馈问题：huangganghust@qq.com



## 感谢

* https://github.com/hongyangAndroid/baseAdapter

  最初看到他ListView的万能适配器博客之后开始研究使用起来的，怎奈里面Bug太多，解了一个又一个，后来改用RecyclerView之后，一怒之下自己封装了一套。里面header、footer、加载更多等采用的装饰者模式的思路不错，与ListView设置headerview的adapter源码类似，但是使用起来感觉有点麻烦和混乱，所以未采用。

* [https://github.com/JoanZapata/base-adapter-helper](https://github.com/JoanZapata/base-adapter-helper)

  参考多种ItemType绑定数据方式。

* [https://github.com/sockeqwe/AdapterDelegates](https://github.com/sockeqwe/AdapterDelegates)

* 以及其他我可能参考了某些思路的开源作者。