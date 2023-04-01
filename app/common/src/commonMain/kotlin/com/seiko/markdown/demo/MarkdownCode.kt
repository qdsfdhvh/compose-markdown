package com.seiko.markdown.demo

val markdownCode = """
    # 1.标题(h标签)
    h1-h6对应1-6个#
    ## h2
    ### h3
    #### h4
    ##### h5
    ###### h6
    This is a **full** on test with some _markdown_!
    What's really cool is the `code snippets` that show up too.
    ###### _~~**`This is a test with every kind of formatting`**~~_
    Inline [`links`](https://google.com) are now also supported! Here is an example of
    [another link](https://tylerbwong.me).
    # 2.列表(li>li)
    #### 无序列表
    * This is an `unordered` list item
    - This is another **unordered** list item
    * This is yet another [unordered](https://tylerbwong.me) list item
    #### 有序列表
    1. This is an `ordered` list item
    2. This is another **ordered** list item
    3) This is yet another [ordered](https://tylerbwong.me) list item
    # 3.引用(blockquote)
    >这个是引用的内容
    # 4.图片与链接
    #### 图片:名字、url
    #### 链接
    [小莫的主页](http://www.xiaomo.info)
    # 5.粗体与斜体
    > 说明：用两个 * 包含一段文本就是粗体的语法，用一个 * 包含一段文本就是斜体的语法。
    **这里是粗体**
    *这里是斜体*
    ~~这是删除线~~
    # 6.表格
    | Tables        | Are           | Cool  |
    | ----------- |:-----------:| -----:|
    | col 3 is      | right-aligned | ${'$'}1600 |
    | col 2 is      | centered      |   ${'$'}12 |
    | zebra stripes | are neat      |    ${'$'}1 |
    # 7.代码块
    ```
    function Hello() {
        console.log("hello");
    }
    ```
    # 8.分割线(hr)
    三个或三个以上的星号、减号或者下划线
    ***

    > I am a block quote!
    > > I am a nested block quote!

    - [ ] I am an ~~unchecked~~ checkbox
    - [x] I am a [checked checkbox](https://google.com)
""".trimIndent()
