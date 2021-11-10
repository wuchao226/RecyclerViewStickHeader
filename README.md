# RecyclerViewStickHeader
RecyclerView 实现吸顶效果项目实战


### 效果展示

![](https://github.com/wuchao226/RecyclerViewStickHeader/blob/master/images/preview.gif)

### 吸顶绘制 计算 示意图
![吸顶绘制](https://github.com/wuchao226/RecyclerViewStickHeader/blob/master/images/%E5%90%B8%E9%A1%B6%E7%BB%98%E5%88%B6.jpg)

### RecyclerView 下的抽象方法 ItemDecoration
**getItemOffsets**：它是用来给Item设置间距的，可以这样理解在Item外还有一层布局，而这个方法是用来设置布局的Padding。

**onDraw**：它的绘制和Item之下，它绘制的东西会在Item的下面。

**onDrawOver**：它的绘制在Item之上，它绘制的东西会覆盖在Item的上面。

最根本的原因是因为它们方法的调用方法的顺序，又因为都作用于同一个 Canvas 上，才出现这种覆盖的层次的效果。

知道了这些方法的作用后，我们配合 RecyclerView 给我们的一些 API 方法，要做其它事情容易多了，随意举2个例子：
1、如果我们想要绘制分割线，只需要先调用 getItemOffsets，让 Item 空出一定的间隙，然后再调用 onDraw 在这个间隙上填充颜色即可。

2、我们经常会遇到一些节假日活动的需求，需要在列表上的边角处标记“活动”，“特价”等特殊符号，这时候我们只需要调用 onDrawOver 在 Item 上绘制即可。

### 绘制Item的分割线和组头 及 onDraw() 
![on_draw](https://github.com/wuchao226/RecyclerViewStickHeader/blob/master/images/on_draw.png)

### 绘制Item的顶部布局（吸顶效果） 及 onDrawOver() 
![on_draw_over](https://github.com/wuchao226/RecyclerViewStickHeader/blob/master/images/on_draw_over.png)

### outRect 设置Item的间距 
![out_rect](https://github.com/wuchao226/RecyclerViewStickHeader/blob/master/images/out_rect.png)
