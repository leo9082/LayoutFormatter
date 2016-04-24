# LayoutFormatter

Retrofit your Android layout XML files.

It will adjust your attributes order in accordance with the rules,
and it will adjust some attributes to the front, and take some attributes at the end of the rows.
It will make your code format more nice.

It is open source. <a href="https://github.com/drakeet/LayoutFormatter">https://github.com/drakeet/LayoutFormatter</a>

![](http://ww4.sinaimg.cn/large/86e2ff85gw1f2t2d40we4j21ge0m5gz1.jpg)

### Obtaining

The plugin is distributed through the Plugin Manager in IntelliJ. https://plugins.jetbrains.com/plugin/8299

Or you can download the jar file from [releases](https://github.com/drakeet/LayoutFormatter/releases).

### Install

`Preferences -> Plugin -> Install plugin from disk...`

### Usage

`YourFiles -> PopupMenu(Right Click) -> Refactor -> Reformat Layout XML.`

Or you could use the default keyboard-shortcut: `ctrl alt F` (`command alt F`).

### Changelog
v1.1.0<br/>
- Adjust the location of the Action to RefactoringMenu;<br/>
- Support UNDO;<br/>
- Filter non XML file;<br/>
- Support one key to format all files on the entire project or folder;<br/>
- Add default keyboard-shortcut: ctrl alt F (command alt F).

v1.0.4<br/>
- Fix the bad style that ">" or "/>" may be moved to a newline;<br/>
- Add "fill_parent" to "match_parent";<br/>
- Add "dip" to "dp".<br/>


### Deprecated

This project is deprecated in favor of the Android Studio has the same and better XML formatter:
`Editor -> Code Style -> XML -> Arrangement`

**However**

I still think this plugin is more useful, more powerful, and more easy to use.



