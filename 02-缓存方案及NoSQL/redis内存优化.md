#### 一、特殊编码：

    自从Redis 2.2之后，很多数据类型都可以通过特殊编码的方式来进行存储空间的优化。其中，Hash、List和由Integer组成的Sets都可以通过该方式来优化存储结构，以便占用更少的空间，在有些情况下，可以省去9/10的空间。
    这些特殊编码对于Redis的使用而言是完全透明的，事实上，它只是CPU和内存之间的一个交易而言。如果内存使用率方面高一些，那么在操作数据时消耗的CPU自然要多一些，反之亦然。在Redis中提供了一组配置参数用于设置与特殊编码相关的各种阈值，如：
    #如果Hash中字段的数量小于参数值，Redis将对该Key的Hash Value采用特殊编码。
    hash-max-zipmap-entries 64
    #如果Hash中各个字段的最大长度不超过512字节，Redis也将对该Key的Hash Value采用特殊编码方式。
    hash-max-zipmap-value 512
    #下面两个参数的含义基本等同于上面两个和Hash相关的参数，只是作用的对象类型为List。
    list-max-ziplist-entries 512
    list-max-ziplist-value 64
    #如果set中整型元素的数量不超过512时，Redis将会采用该特殊编码。
    set-max-intset-entries 512
    倘若某个已经被编码的值再经过修改之后超过了配置信息中的最大限制，那么Redis会自动将其转换为正常编码格式，这一操作是非常快速的，但是如果反过来操作，将一个正常编码的较大值转换为特殊编码，Redis的建议是，在正式做之前最好先简单测试一下转换效率，因为这样的转换往往是非常低效的。
    
#### 二、BIT和Byte级别的操作：

    从Redis 2.2开始，Redis提供了GETRANGE/SETRANGE/GETBIT/SETBIT四个用于字符串类型Key/Value的命令。通过这些命令，我们便可以像操作数组那样来访问String类型的值数据了。比如唯一标识用户身份的ID，可能仅仅是String值的其中一段子字符串。这样就可以通过GETRANGE/SETRANGE命令来方便的提取。再有就是可以使用BITMAP来表示用户的性别信息，如1表示male，0表示female。用这种方式来表示100,000,000个用户的性别信息时，也仅仅占用12MB的存储空间，与此同时，在通过SETBIT/GETBIT命令进行数据遍历也是非常高效的。
    
#### 三、尽可能使用Hash：

    由于小的Hash类型数据占用的空间相对较少，因此我们在实际应用时应该尽可能的考虑使用Hash类型，比如用户的注册信息，这其中包括姓名、性别、email、年龄和口令等字段。我们当然可以将这些信息以Key的形式进行存储，而用户填写的信息则以String Value的形式存储。然而Redis则更为推荐以Hash的形式存储，以上信息则以Field/Value的形式表示。
    现在我们就通过学习Redis的存储机制来进一步证明这一说法。在该篇博客的开始处已经提到了特殊编码机制，其中有两个和Hash类型相关的配置参数：hash-max-zipmap-entries和hash-max-zipmap-value。至于它们的作用范围前面已经给出，这里就不再过多的赘述了。现在我们先假设存储在Hash Value中的字段数量小于hash-max-zipmap-entries，而每个元素的长度又同时小于hash-max-zipmap-value。这样每当有新的Hash类型的Key/Value存储时，Redis都会为Hash Value创建定长的空间，最大可预分配的字节数为:
    total_bytes = hash-max-zipmap-entries * hash-max-zipmap-value
    这样一来，Hash中所有字段的位置已经预留，并且可以像访问数组那样随机的访问Field/Value，他们之间的步长间隔为hash-max-zipmap-value。只有当Hash Value中的字段数量或某一新元素的长度分别超过以上两个参数值时，Redis才会考虑将他们以Hash Table的方式进行重新存储，否则将始终保持这种高效的存储和访问方式。不仅如此，由于每个Key都要存储一些关联的系统信息，如过期时间、LRU等，因此和String类型的Key/Value相比，Hash类型极大的减少了Key的数量(大部分的Key都以Hash字段的形式表示并存储了)，从而进一步优化了存储空间的使用效率。

