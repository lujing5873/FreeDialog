# FreeDialog
### 如何使用
项目gradle下 添加jitpack仓库
```
allprojects {
    repositories {
        maven { url 'https://www.jitpack.io' }
    }
}
```
module.gradle下添加
```
dependencies {
implementation 'com.github.lujing5873:FreeDialog:V1.1.3'
}
```
### 使用

#### 1.继承FreeCusDialog 重写getLayoutId 和 createView方法
```
public class ListDialog extends FreeCusDialog {

    @Override
    public int getLayoutId() {
        return R.layout.dialog_list;
    }

    @Override
    protected void createView(Bundle savedInstanceState) {
        RecyclerView recyclerView=findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        TestAdapter testAdapter=new TestAdapter();
        List<String> list=new ArrayList<>();
        for(int i=0;i<5;i++){
            list.add("第"+i+"条数据");
        }
        testAdapter.submitList(list);
        recyclerView.setAdapter(testAdapter);
    }
}
```
activity中直接调用
```
findViewById(R.id.tv_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ListDialog()
                        .setCancel(false) //不可取消  还有其他属性自行查看
                        .show(getSupportFragmentManager(),"list");
            }
        });
```
#### 2.直接在activity中使用FreeDialog
```
findViewById(R.id.tv_list_free).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FreeDialog.Builder(R.layout.dialog_list) {
                    @Override
                    public void onCreateView(Bundle savedInstanceState) {
                        RecyclerView recyclerView=getViewById(R.id.rv_list);
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        TestAdapter testAdapter=new TestAdapter();
                        List<String> list=new ArrayList<>();
                        for(int i=0;i<5;i++){
                            list.add("第"+i+"条数据");
                        }
                        testAdapter.submitList(list);
                        recyclerView.setAdapter(testAdapter);
                    }
                }.setAnchor(findViewById(R.id.tv_list_free),0,0).setGravity(Gravity.LEFT)
                        .show(getSupportFragmentManager(),"list_free");
            }
        });
```

### 属性说明
#### FreeCusDialog && FreeDialog
setElevation(int dp) 设置阴影 默认为2dp 值为dp值

setDimAmount(float dimAmount)  设置遮罩层透明度 如果没有设置anchorView 默认为0.5F否则为0

setCancel(boolean cancel) 设置是否可以点击外部取消

setAnchor(View anchorView, int xOffset, int yOffset) 设置anchorView  

setGravity(int gravity) 设置gravity

findViewById(@IdRes int id) 根据id查找view

setListener(ViewClick listener) 外部设置点击监听

addViewListener(int ids) 内部根据按钮设置点击监听

setDrag(Boolean canDrag) 设置是否可以长按拖拽

setTimeMills(int timeMills)设置长按时间  默认为300ms

setSoftMode(int softMode) 设置输入法模式 详情请查阅相关资料

 setStyle(int style) 设置window style 通常用于设置window动画等
 
 setTrend(boolean trend) 设置是否动态载入数据


### 更多用法请查看demo
