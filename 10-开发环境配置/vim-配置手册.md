```

 VIM配置 
    
    autocmd! bufwritepost .vimrc source %
    set nocompatible "关闭VI兼容模式
    setl cursorcolumn "高亮光标列 
    setl cursorline "高亮光标行 
    
    set encoding=utf-8  "Vim 内部使用的编码
    let &termencoding=&encoding
    set fileencoding=utf-8
    set fileencodings=ucs-bom,utf-8,chinese "cp936,big5
    set ambiwidth=double "对“不明宽度”字符的处理方式  
    
    set langmenu=zh_CN.UTF-8  "use chinese help
    set helplang=cn
    
    if has("gui_running")
      set guifont=DejaVu\ Sans\ Mono\ 12 "字体设置
    endif
    
    colorscheme desert "只有在这里才可以抑制错误
    syntax on "语法着色
    set ic "搜索不区分大小写
    set hls "设置高亮搜索
    set is ""即时匹配输入
    set nu "自动显示行号
    
    set showcmd "显示输入的命令"
    set showmatch "高亮显示匹配的括号
    set matchtime=1 "匹配括号高亮的时间（单位是十分之一秒）没什么效果
    set statusline=%(\%{VisualSelect()}%)
    set statusline+=\ ☊\ %{$USER}\ \%{getcwd()}\ %f\ %y\ [%{&ff}]\
    set statusline+=[%{strftime(\"%m/%d/%Y\ \%H:%M\",getftime(expand('%')))}\]\ [%l,%v\|%o,%b\]\ [%p%%]\ %m\ %r\
    set laststatus=2 "总是显示状态行
    set cul "突出当前行
    set expandtab "用空格代替制表符"
    set cinoptions=>4,n-2,{2,^-2,:2,=2,g0,h2,p5,t0,+2,(0,u0,w1,m1 shiftwidth=4 tabstop=8 "标准的 GNU 编码缩进风格的设置  shiftwidth原来是2 改C为4
    
    filetype on "侦测文件类型
    filetype plugin on "载入文件类型插件
    filetype indent on "载入相关缩进
    
    au FileType html,vim,javascript setl shiftwidth=4
    au FileType html,vim,javascript setl tabstop=4
    au FileType java,php,python setl shiftwidth=4
    au FileType java,php,python setl tabstop=4
    
    set complete+=k
    au FileType php setlocal dict+=~/.vim/dict/phpfunclist.txt "字典
    au FileType php setlocal iskeyword=@,48-57,_,192-255 "setlocal设置可以避免被后面的plugin覆盖
    
    set wildmenu "自动补全命令时候使用菜单式匹配列表
    set backspace=2 " 允许退格键删除
    set history=1000 " 记录历史 cmd : /
    set magic "关于字符转义方面的"
    
    " When editing a file, always jump to the last known cursor position.
    autocmd BufReadPost *
          \ if line("'\"") > 0 && line("'\"") <= line("$") |
          \ exe "normal g`\"" |
          \ endif
    
    set backspace=start,indent,eol "不设定在插入状态无法用退格键和 Delete 键删除回车符"
    set smarttab "新行智能tab缩进"
    set lbr "单词换行处，整个单词不会折行"
    set ai "oO等新行复制缩进"
    set smartindent "开启智能自动缩进
    set wrap "自动换行"
    set hidden " 允许在有未保存的修改时切换缓冲区，此时的修改由 vim 负责保存
    set scrolloff=10 " 光标移动到buffer的顶部和底部时各保持10行距离 
    "set autochdir
    
" ] 


" 自定方法 [
    
    "显示V模式下的行x列数
    fun! VisualSelect()
        if mode() == "v"
            exe "normal \<ESC>gv"
            if line("'<") != line("'>")
                let dao = (line("'>")-line("'<")+1)
            else
                let dao = (col("'>")-col("'<")+1)
            endif
        elseif mode() == "V"
            exe "normal \<ESC>gv"
            let dao = (line("'>")-line("'<")+1)
        elseif mode() == "\<C-V>"
            exe "normal \<ESC>gv"
            let dao = (line("'>")-line("'<")+1).'x'.(abs(col("'>")-col("'<"))+1)
        endif
        return exists('dao') ? '['.dao.'] ' : ''
    endfun
    
    "vim 打开PHP手册
    "需求, 打开光标所在位置的函数 >"http://www.php.net/manual/zh/function.in-array.php √
    com! -nargs=+ Php  call OpenPhpFunction(<q-args>)
    map gp :Php <c-r><c-w><CR> "<silent>
    fun! OpenPhpFunction (keyword)
        let proc_keyword = substitute(a:keyword , '_', '-', 'g')
        exe 'split'                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
        exe 'enew'
        exe "set buftype=nofile"
        exe 'silent r!lynx -dump -nolist http://www.php.net/manual/zh/function.'.proc_keyword.'.php'
        exe 'norm gg'
        exe 'call search ("' . a:keyword .'")'
        exe 'norm dgg'
        exe 'call search("User Contributed Notes")'
        exe 'norm dGgg'
    endfun
" ] 