# git tips

### tip 1

git 保存的信息是每次变动 文件名称 内容 读写属性的改变都视为变动 比如:

	
```
vim test1.txt 添加1行 line1
git add
vim test1.txt 再添加1行 line 2
git commit -m "add file test1"
```

此时本地仓库保存到 test1.txt 的内容 是 line 1 只有1行 因为第二次修改的变动 没有 git add

### tip 2

如上 chmod一个文件的读写属性 也被视为变动 但是git远程分支默认最高权限到 755 所以本地的 777 提交到远程 保存的读写属性值还是 755

### tip 3

git branch -a 红色的(如果终端没有配置颜色显示 看不到红色)分支 如


```
remotes/origin/master
```

可以视为远程分支在本地的镜像 在本地可以一样正常操作 如

```
git checkout remotes/origin/master
```

还可以简写为

```
git checkout origin/master
```

### tip 4

上述的远程分支 可以用fetch 指令同步远程最新变动到本地 如

```
git fetch origin master
```

还可以同步远程所有最新变动到本地

```
git fetch origin
```

远程删除或新建的分支 也一样会同步到本地 即本地以 remotes/origin 开头的分支

### tip 5

```
git pull origin master = git fetch origin master && git merge origin/master
```

### tip 6

远程新加了test分支 希望建立对应的本地分支

```
git fetch origin

git checkout origin/test

(这个时候如果用git branch看分支状态 会发现本地在一个没有名字的分支上)

git checkout -b test

(等同于 git branch test && git checkout test)

```

上述的操作中 本地的分支名其实也可以不同 如

```
git checkout origin/test

git checkout -b local-test
```

则本地新增local-test分支 内容与origin/test 一样 后续可以pull远程内容到本地分支

```
git pull origin test:local-test
```

可以将本地的分支与远程的做关联

```
git branch --set-upstream local-test origin/test
```

关联之后 命令可以精简

```
git pull origin local-test
(等同于 git pull origin test:local-test)
git push origin local-test
(等同于 git push origin test:local-test)
```

但是约定俗成 最好在本地和远程使用同样的分支名称

```
git checkout origin/test
git checkout -b test
```

一样pull远程内容到本地分支

```
git pull origin test:test
```

本地和远程分支名称一样 git默认可以使用简写形式 并自动建立到远程的同名关联

```
git pull origin test
(等同于 git branch --set-upstream test origin/test && git pull origin test:test)
```

### tip 7

上述 远程分支在本地可以同样操作

```
git fetch origin
git diff test..origin/test
(比如本地test分支 和 远程 test分支)
git diff test..origin/master
(比对本地test分支 和 远程 master分支)
git checkout test && git merge origin/master
(远程的master分支 合并到本地test分支)
```

### tip 8

push 或者 merge request之前 可以先在本地模拟一次 保证没有冲突 如

```
git checkout master
git checkout -b test
(等同于 git branch test && git checkout test)
git merge origin/master
```

在本地新建一个test分支 并模拟 本地master和远程master 合并的情况

### tip 9

合并的时候 可以用 --no-ff 参数 避免fast forward方式合并 合并前后都保留分支节点

更容易在log等历史信息中展示

```
git merge --no-ff test
git checkout master && git merge --no-ff origin/master
```

详见: http://www.ruanyifeng.com/blog/2012/07/git.html

pull的时候 可以用 --rebase 参数 避免自动合并产生的 类似 "Merge branch 'test' of ..." 提交

```
git pull --rebase origin master
```

### tip 10

git的分布式 需要理解 没有中心服务器的概念

远程的服务器 可以视为只是一个交换空间 交换不同节点之间的提交

所有远程server上有的commit、log等等 都是来自于某个具体的client

```
场景:
a添加 test1.txt 文件1行内容 line1 提交 并推到远程
git add test1.txt
git commit -m "add file test1"
git push origin master

b更新 添加 test2.txt test3.txt 提交 并推到远程
git pull origin master
git add test2.txt test3.txt
git commit -m "add file test2 and test3"
git push origin master

b修改 test2.txt 增加1行内容 line 1 提交 并推到远程
vim test2.txt
git add test2.txt
git commit -m "add test2 content"
git push origin master

a修改 test1.txt 增加1行内容 line 2
vim test1.txt

a尝试提交
git push origin master
得到错误提示
a尝试更新
git pull origin master
成功更新到最新状态
git push origin master
成功推到远程

此时查看提交的log
git log --author=a -1
得到commit号 123456789
git show 123456789
展示信息中diff部分 显示:
test1 增加1行内容 line 2
test2 增加1行内容 line 1
增加了文件 test3

如果使用svn等中心服务器模式 提交信息 应该只有 a修改的部分
test1 增加1行内容 line 2
但是git的提交信息 却包含了所有a和b的修改 为何?
因为a的git pull origin master 默认产生了一次merge的提交
将a的最新变动
test1 增加1行内容 line 2
b的最新变动
test2 增加1行内容 line 1
增加test3文件
进行了合并
```

### tip 11
远程的master分支保护起来 不可以直接提交

本地新建一个自己的分支

```
git checkout master
git checkout -b weide
(git branch weide && git checkout weide)
```

在自己的分支上开发并提交代码

```
git commit -m "test"
```

自己的分支推到远程

```
git push origin weide
```

开发完毕 先本地尝试合并远程分支

```
git fethc origin master
git merge --no-ff origin/master
```

如果有冲突 手工解决 如文件a冲突

```
vim a
git add a
git commit -m "fix a conflict"
```

推到远程

```
git push origin weide
```

在页面上发起一个merge request weide -> master

怕没有把握 搞乱了本地代码 可以在本地再切个分支处理

```
git checkout weide
git checkout -b weide-temp
git merge --no-ff origin/master
.......
(处理完所有冲突等 已经有把握没有问题)
git checkout weide
git merge weide-temp
git push origin weide
(页面上发起merge request)
```