 # Patronus[ ![Download](https://api.bintray.com/packages/wildcreek/maven/patronus/images/download.svg) ](https://bintray.com/wildcreek/maven/patronus/_latestVersion)

### 本项目大量参考了以下文章和项目，按照个人需求修改了部分代码，仅供参考

>[探讨Android6.0及以上系统APP常驻内存(保活)实现-jiangdongguo](https://github.com/jiangdongguo/KeepingAppAlive.git)</br>
>[Android进程保活招式大全 -腾讯 张兴华](http://dev.qq.com/topic/57ac4a0ea374c75371c08ce8)</br>
>[Android 进程常驻-Marswin89](http://blog.csdn.net/Marswin89)</br>

---------------------------------------
> 在遇到crash、系统回收机制回收（进程优先级、doze模式）、用户触发（滑动清理等）、锁屏黑屏、厂商定制回收机制、三方管家应用清理、后台驻留断网（如小米神隐模式）等情况下，Android应用会被杀死，或无法正常连接网络，造成无法正常提供服务。为向用户提供尽最大努力交付的高可用视频通话服务，视频通话应用需要在进程保活、网络保活等方向进行优化。
---------------------------------------
## 第一类：进程保活，分为三个范畴：提权保活、杀死重启、休眠唤醒。

### 被杀原因：
1. crash
2. Android进程回收机制回收
3. 用户触发（一键清理/滑动清理/强制停止）
4. 厂商定制回收机制
5. 各类APP管家应用清理回收
### 可选方案：（勾选中的为建议方案，不同手机效果不尽相同，需要进行系统适配【各手机品牌、型号、rom版本、Android版本】）

休眠唤醒类：
- [x] 1、系统广播唤醒（如启动、拍照、网络变更、sd卡挂载广播）
- [ ] 2、 三方SDK唤醒（如支付sdk，推送sdk）【高可用，但需要额外集成如信鸽推送等sdk】
- [ ] 3、 全家桶唤醒（如某系列应用启动唤醒全家桶）【无全家桶应用，待研究】
- [x] 4、 JobScheduler （>= API 21）唤醒
- [ ] 5、 全局定时器唤醒【高版本不可用，随进程死亡失效】

杀死重启类：
- [x] 6、 service 返回值 start_sticky死亡重启
- [ ] 7、 单进程双service互拉重启【高版本不可用，随进程死亡失效】
- [ ] 8、 双进程多service绑定互拉方案【额外安装应用，不推荐】
- [ ] 9、 NDK fork子进程与父进程互拉重启【4.x有效，高版本不可用，随进程死亡失效】

提权保活类：
- [x] 10、 前台service提权保活（可隐藏显示）
- [x] 11、 锁屏1像素Activity保活
- [ ] 12、 循环播放无声音频保活【耗电，不推荐】

## 第二类：网络保活【不同ROM后台驻留断网机制不相同，需要预留大量时间测试调研，未详细研究】

### 断网原因：
1. 小米神隐模式
2. 华为特定机型，机制尚不明确
3. 其他机型，尚未测试
### 可选方案：
1. GCM推送【google服务国内不可用】
2. 三方推送 【尚未验证】
3. 服务器轮询、长连接【尚未验证】

## 其他类  ：网络诊断

1. 微信开源Mars SDT网络诊断组件，对网络异常进行诊断提示

---------------------------------------
## 方案适配（待完善）
## 适配终端
品牌|型号|ROM版本|Android版本
## 方案测试（待完善）
### 适配终端
品牌|型号|ROM版本|Android版本
-|-|-|-
小米|MI 5|MIUI 8.2|7.0
红米|Redmi Note 4x|MIUI 8.1|6.0.1
华为|Mate 7|EMUI 3.0|4.4.2
华为|畅享 5S|EMUI 3.1|5.1
Google|Nexus 5|CM |6.0.1
魅族|MX4 Pro |Flyme OS 4.1.1.3C |4.4.4
中兴|B2015 |MiFavor UI 3.2.0 |5.1.1
### 测试方法
1. 判断Service是否位于前台
```
dumpsys activity services com.wildcreek.patronus
```
2. 查看oom_adj值
```
adb shell
ps | grep com.wildcreek.patronus
cat /proc/pid/oom_adj
```
