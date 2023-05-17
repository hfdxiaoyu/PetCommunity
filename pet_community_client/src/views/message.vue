<!-- 留言评论模块 -->
<template>
    <div class="tmsgBox"  ref="tmsgBox">
        <div class="tmsg-respond"  ref="respondBox">
            <h3>发表评论 <small v-show="isRespond" class="tcolorm" @click="removeRespond">取消回复</small></h3>
            <form class=""  >
                <el-input
                  type="textarea"
                  :rows="2"
                  placeholder="说点什么呢``"
                  v-model="textarea">
                </el-input>

                <el-row class="tmsg-r-info">
                    <el-col :span="24" class="info-submit">
                        <p class="tcolors-bg" @click="sendMsg">{{sendTip}}</p>
                    </el-col>
                </el-row>
            </form>
        </div>
        <div class="tmsg-comments"  ref="listDom">
            <a href="#" class="tmsg-comments-tip">活捉 {{commentList?commentList.length:0}} 条</a>
            <div class="tmsg-commentshow">
                <ul class="tmsg-commentlist">
                    <li class="tmsg-c-item" v-for="(item,index) in commentList" :key="'common'+index">
                        <article class="">
                            <header>
                                <img  :src="$store.state.errorImg"  :onerror="$store.state.errorImg">
                                <div class="i-name">
                                    {{item.username}}
                                </div>
                                <!-- <div class="i-class">
                                    {{item.label}}
                                </div> -->
                                <div class="i-time">
                                    <time>{{item.createTime}}</time>
                                </div>
                            </header>
                            <section>
                                <p >{{item.content}}</p>
                                <div v-if="haslogin" class="tmsg-replay" @click="respondMsg(item.id,item.id,item.createBy)">
                                    回复
                                </div>
                            </section>
                        </article>
                        <ul v-show="item.children" class="tmsg-commentlist" style="padding-left:60px;">
                            <li class="tmsg-c-item" v-for="(citem,cindex) in item.children" :key="'citem'+cindex">
                                <article class="">
                                    <header>
                                            <img :src="$store.state.errorImg"  :onerror="$store.state.errorImg">
                                            <div class="i-name">
                                                {{citem.username}} <span>回复</span> {{citem.toCommentUserName}}
                                            </div>
                                            <div class="i-time">
                                                <time>{{citem.createTime}}</time>
                                            </div>
                                    </header>
                                    <section>
                                        <p>{{citem.content}}</p>
                                        <div v-show="haslogin" class="tmsg-replay" @click="respondMsg(item.id,citem.id,citem.createBy)">
                                            回复
                                        </div>
                                    </section>
                                </article>
                            </li>
                        </ul>
                    </li>

                </ul>
                <h1 v-show='hasMore' class="tcolors-bg" @click="addMoreFun" >查看更多</h1>
                <h1 v-show='!hasMore' class="tcolors-bg" >没有更多</h1>
            </div>
        </div>
    </div>
</template>

<script>
    // import {sendComment,getLinkComment} from '../api/comment.js'
    export default {
      name:"message",
        data() { //选项 / 数据
            return {
                respondBox:'',//评论表单
                listDom:'',//评论列表
                tmsgBox:'',//总评论盒子
                // 查询参数
                queryParams: {
                    pageNum: 1,
                    pageSize: 10,
                    articleId: 0
                },
                isRespond:false,
                textarea: '',//文本框输入内容
                pBody:true,//表情打开控制
                commentList:[],//评论列表数据
                aid:0,//文章id
                hasMore:false,
                haslogin:true,
                userId:'',//用户id
                type:0,//回复评论的当前的commentId
                leavePid:'',//赞赏等其他模块的分类id
                pid:'',//回复评论的一级commentId
                rootId:-1,//根评论id，如果是针对文字评论直接用-1表示
                toCommentId:-1,//所回复评论的id
                toCommentUserId:-1,//所评论的用户id
                sendTip:'发送~',

            }
        },
        methods: { //事件处理器
            setData(initData,result){
                var msg = result.data.data.rows;
                if(initData){
                    //刷新列表
                    this.commentList = msg;
                }else{
                    //加载更多
                    this.commentList = this.commentList.concat(msg);
                }

                this.hasMore = result.total>this.commentList.length
              },
          //选择表情包
          choseEmoji:function(inner){
              this.textarea +='[' + inner + ']';
          },
          //发送留言
          sendMsg:function(){//留言
          //     var that = this;
          //     if(that.textarea){
          //         that.sendTip = '咻~~';
          //           sendComment(that.type,that.aid,that.rootId,that.toCommentId,that.toCommentUserId,that.textarea).then((response)=>{
          //               that.textarea = '';
          //               that.rootId = -1;
          //               that.toCommentId = -1;
          //               that.toCommentUserId=-1;
          //
          //               that.routeChange();
          //               that.removeRespond();
          //               var timer02 = setTimeout(function(){
          //                   that.sendTip = '发送~';
          //                   clearTimeout(timer02);
          //               },1000)
          //           })
          //     }else{
          //         that.sendTip = '内容不能为空~'
          //         var timer = setTimeout(function(){
          //             that.sendTip = '发送~';
          //             clearTimeout(timer);
          //         },3000)
          //     }
          },
          respondMsg:function(rootId,toCommentId,toCommentUserId){//回复留言
              // console.log(leavePid,pid);
              var that = this;
              if(localStorage.getItem('userInfo')){
                  var dom = event.currentTarget;
                  dom = dom.parentNode;
                  this.isRespond = true;
                //   this.leavePid = leavePid;
                  this.rootId = rootId
                  this.toCommentId = toCommentId;
                  this.toCommentUserId = toCommentUserId
                  dom.appendChild(this.$refs.respondBox);
              }else{
                  that.$confirm('登录后即可点赞和收藏，是否前往登录页面?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                    }).then(() => {//确定，跳转至登录页面
                        //储存当前页面路径，登录成功后跳回来
                        localStorage.setItem('logUrl',that.$route.fullPath);
                        that.$router.push({path:'/Login?login=1'});
                   }).catch(() => {

                   });
              }
          },
          removeRespond:function(){//取消回复留言
            this.isRespond = false;
            this.rootId = -1;
            this.toCommentId = -1;
            this.toCommentUserId=-1;
            this.$refs.tmsgBox.insertBefore(this.$refs.respondBox,this.$refs.listDom);
          },
          showCommentList: function(initData){//评论列表
              var that = this;
              that.aid = that.$route.query.contenid==undefined?1:parseInt(that.$route.query.contenid);//获取传参的aid
              that.queryParams.articleId = that.aid
              //判断当前用户是否登录
              // var token = getToken();
              if(this.$store.state.isLogin){
                  that.haslogin = true;
              }else{
                  that.haslogin = false;
              }

              //公用设置数据方法
              this.getArticleComment(initData)
              // if(that.$route.name=='DetailArticle'){//文章列表的评论
                  // that.type = 0;
                  // this.getArticleComment(that.queryParams).then((response)=>{
                  //    that.setData(initData,response);
                  // })
                  // this.getArticleComment(initData)

              // }
              // else{//其他评论
              //       if(that.$route.name == 'FriendsLink'){
              //         that.type = 1
              //         getLinkComment(that.queryParams).then((response)=>{
              //             that.setData(initData,response);
              //         })
              //     }
              // }
          },
          addMoreFun:function(){//查看更多
              this.queryParams.pageNum++
              this.showCommentList(false);
          },
          routeChange:function(){//重新加载
              var that = this;
              this.queryParams.pageNum = 1
              this.showCommentList(true);
          },
          getArticleComment(initData){
              if (this.$store.state.isLogin){
                //请求评论
                this.request.get('/bbsReplay/replayList',{
                  params:{
                    pageNum:this.queryParams.pageNum,
                    pageSize:this.queryParams.pageSize,
                    articleId : this.queryParams.articleId, //获取页面跳转传过来的文章id
                  }
                }).then(
                  res => {
                    this.setData(initData,res)
                  }

                )

              }else {
                this.$message.error("登录后才能查看评论哦...")
              }

          }
        },
        components: { //定义组件

        },
        watch: {
           // 如果路由有变化，会再次执行该方法
           // '$route':'routeChange'
         },
        created() { //生命周期函数
            // console.log(this.$route);
            var that = this;
            that.showCommentList(true)
            // that.routeChange();
        },
        mounted(){//页面加载完成后

        }
    }
</script>

<style>
.tmsgBox{
  position: relative;
  background: #fff;
  padding:15px;
  margin-bottom: 20px;
  border-radius: 5px;
}
.tmsg-respond h3{
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 8px;
}
.tmsg-respond h3 small{
  font-size: smaller;
  cursor: pointer;
}
.tmsg-respond textarea{
  background:#f4f6f7;
  height:100px;
  margin-bottom: 10px;
}
.OwO{
  position: relative;
  z-index: 1;
}
.OwO .OwO-logo{
  position: relative;
  border-radius: 4px;
  color:#444;
  display: inline-block;
  background: #fff;
  border:1px solid #ddd;
  font-size: 13px;
  padding:0 6px;
  cursor: pointer;
  height:30px;
  box-sizing: border-box;
  z-index: 2;
  line-height: 30px;
}
.OwO .OwO-logo:hover{
  animation:a 5s infinite ease-in-out;
  -webkit-animation:a 5s infinite ease-in-out;
}
.OwO .OwO-body{
  position: absolute;
  background: #fff;
  border:1px solid #ddd;
  z-index: 1;
  top:29px;
  border-radius: 0 4px 4px 4px;
  display: none;
}
.OwO-open .OwO-body{
  display: block;
}
.OwO-open .OwO-logo{
  border-radius: 4px 4px 0 0;
  border-bottom: none;
}
.OwO-open .OwO-logo:hover{
  animation:none;
  -webkit-animation:none;
}
.OwO .OwO-items {
  max-height: 197px;
  overflow: scroll;
  font-size: 0;
  padding:10px;
  z-index: 1
}
.OwO .OwO-items .OwO-item{
  background: #f7f7f7;
  padding:5px 10px;
  border-radius: 5px;
  display: inline-block;
  margin: 0 10px 12px 0;
  transition: 0.3s;
  line-height: 19px;
  font-size: 20px;
  cursor: pointer;
}
.OwO .OwO-items .OwO-item:hover{
  background: #eee;
  box-shadow: 0 2px 2px 0 rgba(0,0,0,.14),
  0 3px 1px -2px rgba(0,0,0,.2),
  0 1px 5px 0 rgba(0,0,0,.12);
  animation:a 5s infinite ease-in-out;
  -webkit-animation:a 5s infinite ease-in-out;
}
.OwO .OwO-body .OwO-bar{
  width:100%;
  height:30px;
  border-top: 1px solid #ddd;
  background: #fff;
  border-radius: 0 0 4px 4px;
  color: #444;
}
.OwO .OwO-body .OwO-bar .OwO-packages li{
  display: inline-block;
  line-height: 30px;
  font-size: 14px;
  padding: 0 10px;
  cursor: pointer;
  margin-right: 3px;
  text-align: center;
}
.OwO .OwO-body .OwO-bar .OwO-packages li:first-of-type{
  border-radius: 0 0 0 3px;
}
@-webkit-keyframes a{
  2%{-webkit-transform:translateY(1.5px) rotate(1.5deg);transform:translateY(1.5px) rotate(1.5deg)}
  4%{-webkit-transform:translateY(-1.5px) rotate(-.5deg);transform:translateY(-1.5px) rotate(-.5deg)}
  6%{-webkit-transform:translateY(1.5px) rotate(-1.5deg);transform:translateY(1.5px) rotate(-1.5deg)}
  8%{-webkit-transform:translateY(-1.5px) rotate(-1.5deg);transform:translateY(-1.5px) rotate(-1.5deg)}
  10%{-webkit-transform:translateY(2.5px) rotate(1.5deg);transform:translateY(2.5px) rotate(1.5deg)}
  12%{-webkit-transform:translateY(-.5px) rotate(1.5deg);transform:translateY(-.5px) rotate(1.5deg)}
  14%{-webkit-transform:translateY(-1.5px) rotate(1.5deg);transform:translateY(-1.5px) rotate(1.5deg)}
  16%{-webkit-transform:translateY(-.5px) rotate(-1.5deg);transform:translateY(-.5px) rotate(-1.5deg)}
  18%{-webkit-transform:translateY(.5px) rotate(-1.5deg);transform:translateY(.5px) rotate(-1.5deg)}
  20%{-webkit-transform:translateY(-1.5px) rotate(2.5deg);transform:translateY(-1.5px) rotate(2.5deg)}
  22%{-webkit-transform:translateY(.5px) rotate(-1.5deg);transform:translateY(.5px) rotate(-1.5deg)}
  24%{-webkit-transform:translateY(1.5px) rotate(1.5deg);transform:translateY(1.5px) rotate(1.5deg)}
  26%{-webkit-transform:translateY(.5px) rotate(.5deg);transform:translateY(.5px) rotate(.5deg)}
  28%{-webkit-transform:translateY(.5px) rotate(1.5deg);transform:translateY(.5px) rotate(1.5deg)}
  30%{-webkit-transform:translateY(-.5px) rotate(2.5deg);transform:translateY(-.5px) rotate(2.5deg)}
  32%,34%{-webkit-transform:translateY(1.5px) rotate(-.5deg);transform:translateY(1.5px) rotate(-.5deg)}
  36%{-webkit-transform:translateY(-1.5px) rotate(2.5deg);transform:translateY(-1.5px) rotate(2.5deg)}
  38%{-webkit-transform:translateY(1.5px) rotate(-1.5deg);transform:translateY(1.5px) rotate(-1.5deg)}
  40%{-webkit-transform:translateY(-.5px) rotate(2.5deg);transform:translateY(-.5px) rotate(2.5deg)}
  42%{-webkit-transform:translateY(2.5px) rotate(-1.5deg);transform:translateY(2.5px) rotate(-1.5deg)}
  44%{-webkit-transform:translateY(1.5px) rotate(.5deg);transform:translateY(1.5px) rotate(.5deg)}
  46%{-webkit-transform:translateY(-1.5px) rotate(2.5deg);transform:translateY(-1.5px) rotate(2.5deg)}
  48%{-webkit-transform:translateY(-.5px) rotate(.5deg);transform:translateY(-.5px) rotate(.5deg)}
  50%{-webkit-transform:translateY(.5px) rotate(.5deg);transform:translateY(.5px) rotate(.5deg)}
  52%{-webkit-transform:translateY(2.5px) rotate(2.5deg);transform:translateY(2.5px) rotate(2.5deg)}
  54%{-webkit-transform:translateY(-1.5px) rotate(1.5deg);transform:translateY(-1.5px) rotate(1.5deg)}
  56%{-webkit-transform:translateY(2.5px) rotate(2.5deg);transform:translateY(2.5px) rotate(2.5deg)}
  58%{-webkit-transform:translateY(.5px) rotate(2.5deg);transform:translateY(.5px) rotate(2.5deg)}
  60%{-webkit-transform:translateY(2.5px) rotate(2.5deg);transform:translateY(2.5px) rotate(2.5deg)}
  62%{-webkit-transform:translateY(-.5px) rotate(2.5deg);transform:translateY(-.5px) rotate(2.5deg)}
  64%{-webkit-transform:translateY(-.5px) rotate(1.5deg);transform:translateY(-.5px) rotate(1.5deg)}
  66%{-webkit-transform:translateY(1.5px) rotate(-.5deg);transform:translateY(1.5px) rotate(-.5deg)}
  68%{-webkit-transform:translateY(-1.5px) rotate(-.5deg);transform:translateY(-1.5px) rotate(-.5deg)}
  70%{-webkit-transform:translateY(1.5px) rotate(.5deg);transform:translateY(1.5px) rotate(.5deg)}
  72%{-webkit-transform:translateY(2.5px) rotate(1.5deg);transform:translateY(2.5px) rotate(1.5deg)}
  74%{-webkit-transform:translateY(-.5px) rotate(.5deg);transform:translateY(-.5px) rotate(.5deg)}
  76%{-webkit-transform:translateY(-.5px) rotate(2.5deg);transform:translateY(-.5px) rotate(2.5deg)}
  78%{-webkit-transform:translateY(-.5px) rotate(1.5deg);transform:translateY(-.5px) rotate(1.5deg)}
  80%{-webkit-transform:translateY(1.5px) rotate(1.5deg);transform:translateY(1.5px) rotate(1.5deg)}
  82%{-webkit-transform:translateY(-.5px) rotate(.5deg);transform:translateY(-.5px) rotate(.5deg)}
  84%{-webkit-transform:translateY(1.5px) rotate(2.5deg);transform:translateY(1.5px) rotate(2.5deg)}
  86%{-webkit-transform:translateY(-1.5px) rotate(-1.5deg);transform:translateY(-1.5px) rotate(-1.5deg)}
  88%{-webkit-transform:translateY(-.5px) rotate(2.5deg);transform:translateY(-.5px) rotate(2.5deg)}
  90%{-webkit-transform:translateY(2.5px) rotate(-.5deg);transform:translateY(2.5px) rotate(-.5deg)}
  92%{-webkit-transform:translateY(.5px) rotate(-.5deg);transform:translateY(.5px) rotate(-.5deg)}
  94%{-webkit-transform:translateY(2.5px) rotate(.5deg);transform:translateY(2.5px) rotate(.5deg)}
  96%{-webkit-transform:translateY(-.5px) rotate(1.5deg);transform:translateY(-.5px) rotate(1.5deg)}
  98%{-webkit-transform:translateY(-1.5px) rotate(-.5deg);transform:translateY(-1.5px) rotate(-.5deg)}
  0%,to{-webkit-transform:translate(0) rotate(0deg);transform:translate(0) rotate(0deg)}
}
@keyframes a{
  2%{-webkit-transform:translateY(1.5px) rotate(1.5deg);transform:translateY(1.5px) rotate(1.5deg)}
  4%{-webkit-transform:translateY(-1.5px) rotate(-.5deg);transform:translateY(-1.5px) rotate(-.5deg)}
  6%{-webkit-transform:translateY(1.5px) rotate(-1.5deg);transform:translateY(1.5px) rotate(-1.5deg)}
  8%{-webkit-transform:translateY(-1.5px) rotate(-1.5deg);transform:translateY(-1.5px) rotate(-1.5deg)}
  10%{-webkit-transform:translateY(2.5px) rotate(1.5deg);transform:translateY(2.5px) rotate(1.5deg)}
  12%{-webkit-transform:translateY(-.5px) rotate(1.5deg);transform:translateY(-.5px) rotate(1.5deg)}
  14%{-webkit-transform:translateY(-1.5px) rotate(1.5deg);transform:translateY(-1.5px) rotate(1.5deg)}
  16%{-webkit-transform:translateY(-.5px) rotate(-1.5deg);transform:translateY(-.5px) rotate(-1.5deg)}
  18%{-webkit-transform:translateY(.5px) rotate(-1.5deg);transform:translateY(.5px) rotate(-1.5deg)}
  20%{-webkit-transform:translateY(-1.5px) rotate(2.5deg);transform:translateY(-1.5px) rotate(2.5deg)}
  22%{-webkit-transform:translateY(.5px) rotate(-1.5deg);transform:translateY(.5px) rotate(-1.5deg)}
  24%{-webkit-transform:translateY(1.5px) rotate(1.5deg);transform:translateY(1.5px) rotate(1.5deg)}
  26%{-webkit-transform:translateY(.5px) rotate(.5deg);transform:translateY(.5px) rotate(.5deg)}
  28%{-webkit-transform:translateY(.5px) rotate(1.5deg);transform:translateY(.5px) rotate(1.5deg)}
  30%{-webkit-transform:translateY(-.5px) rotate(2.5deg);transform:translateY(-.5px) rotate(2.5deg)}
  32%,34%{-webkit-transform:translateY(1.5px) rotate(-.5deg);transform:translateY(1.5px) rotate(-.5deg)}
  36%{-webkit-transform:translateY(-1.5px) rotate(2.5deg);transform:translateY(-1.5px) rotate(2.5deg)}
  38%{-webkit-transform:translateY(1.5px) rotate(-1.5deg);transform:translateY(1.5px) rotate(-1.5deg)}
  40%{-webkit-transform:translateY(-.5px) rotate(2.5deg);transform:translateY(-.5px) rotate(2.5deg)}
  42%{-webkit-transform:translateY(2.5px) rotate(-1.5deg);transform:translateY(2.5px) rotate(-1.5deg)}
  44%{-webkit-transform:translateY(1.5px) rotate(.5deg);transform:translateY(1.5px) rotate(.5deg)}
  46%{-webkit-transform:translateY(-1.5px) rotate(2.5deg);transform:translateY(-1.5px) rotate(2.5deg)}
  48%{-webkit-transform:translateY(-.5px) rotate(.5deg);transform:translateY(-.5px) rotate(.5deg)}
  50%{-webkit-transform:translateY(.5px) rotate(.5deg);transform:translateY(.5px) rotate(.5deg)}
  52%{-webkit-transform:translateY(2.5px) rotate(2.5deg);transform:translateY(2.5px) rotate(2.5deg)}
  54%{-webkit-transform:translateY(-1.5px) rotate(1.5deg);transform:translateY(-1.5px) rotate(1.5deg)}
  56%{-webkit-transform:translateY(2.5px) rotate(2.5deg);transform:translateY(2.5px) rotate(2.5deg)}
  58%{-webkit-transform:translateY(.5px) rotate(2.5deg);transform:translateY(.5px) rotate(2.5deg)}
  60%{-webkit-transform:translateY(2.5px) rotate(2.5deg);transform:translateY(2.5px) rotate(2.5deg)}
  62%{-webkit-transform:translateY(-.5px) rotate(2.5deg);transform:translateY(-.5px) rotate(2.5deg)}
  64%{-webkit-transform:translateY(-.5px) rotate(1.5deg);transform:translateY(-.5px) rotate(1.5deg)}
  66%{-webkit-transform:translateY(1.5px) rotate(-.5deg);transform:translateY(1.5px) rotate(-.5deg)}
  68%{-webkit-transform:translateY(-1.5px) rotate(-.5deg);transform:translateY(-1.5px) rotate(-.5deg)}
  70%{-webkit-transform:translateY(1.5px) rotate(.5deg);transform:translateY(1.5px) rotate(.5deg)}
  72%{-webkit-transform:translateY(2.5px) rotate(1.5deg);transform:translateY(2.5px) rotate(1.5deg)}
  74%{-webkit-transform:translateY(-.5px) rotate(.5deg);transform:translateY(-.5px) rotate(.5deg)}
  76%{-webkit-transform:translateY(-.5px) rotate(2.5deg);transform:translateY(-.5px) rotate(2.5deg)}
  78%{-webkit-transform:translateY(-.5px) rotate(1.5deg);transform:translateY(-.5px) rotate(1.5deg)}
  80%{-webkit-transform:translateY(1.5px) rotate(1.5deg);transform:translateY(1.5px) rotate(1.5deg)}
  82%{-webkit-transform:translateY(-.5px) rotate(.5deg);transform:translateY(-.5px) rotate(.5deg)}
  84%{-webkit-transform:translateY(1.5px) rotate(2.5deg);transform:translateY(1.5px) rotate(2.5deg)}
  86%{-webkit-transform:translateY(-1.5px) rotate(-1.5deg);transform:translateY(-1.5px) rotate(-1.5deg)}
  88%{-webkit-transform:translateY(-.5px) rotate(2.5deg);transform:translateY(-.5px) rotate(2.5deg)}
  90%{-webkit-transform:translateY(2.5px) rotate(-.5deg);transform:translateY(2.5px) rotate(-.5deg)}
  92%{-webkit-transform:translateY(.5px) rotate(-.5deg);transform:translateY(.5px) rotate(-.5deg)}
  94%{-webkit-transform:translateY(2.5px) rotate(.5deg);transform:translateY(2.5px) rotate(.5deg)}
  96%{-webkit-transform:translateY(-.5px) rotate(1.5deg);transform:translateY(-.5px) rotate(1.5deg)}
  98%{-webkit-transform:translateY(-1.5px) rotate(-.5deg);transform:translateY(-1.5px) rotate(-.5deg)}
  0%,to{-webkit-transform:translate(0) rotate(0deg);transform:translate(0) rotate(0deg)}
}
/*用户输入表单*/
.tmsg-r-info{
  margin:10px 0;
}
.tmsg-r-info input{
  height:30px;
  border-radius: 4px;
  background: #f4f6f7;
}
.tmsg-r-info .info-submit{
  margin: 10px 0;
  text-align: center;
}
.tmsg-r-info .info-submit p,.tmsg-commentshow h1{
  /*background: #97dffd;*/
  color:#fff;
  border-radius: 5px;
  cursor: pointer;
  /*transition: all .3s ease-in-out;*/
  height:30px;
  line-height: 30px;
  text-align: center;
}
/*.tmsg-r-info .info-submit p:hover{
    background: #47456d;
}*/
/*评论列表*/
.tmsg-comments .tmsg-comments-tip{
  display: block;
  border-left: 2px solid #363d4c;
  padding: 0 10px;
  margin: 40px 0;
  font-size: 20px;
}
.tmsg-commentlist {
  margin-bottom:20px;

}
.tmsg-commentshow>.tmsg-commentlist{
  border-bottom: 1px solid #e5eaed;
}
.tmsg-c-item{
  border-top: 1px solid #e5eaed;
}
.tmsg-c-item article{
  margin:20px 0;
}
.tmsg-c-item article header{
  margin-bottom:10px;
}
.tmsg-c-item article header img{
  width: 65px;
  height: 65px;
  border-radius: 50%;
  float: left;
  transition: all .4s ease-in-out;
  -webkit-transition: all .4s ease-in-out;
  margin-right: 15px;
  object-fit: cover;
}
.tmsg-c-item article header img:hover{
  transform: rotate(360deg);
  -webkit-transform: rotate(360deg);
}
.tmsg-c-item article header .i-name{
  font-size: 14px;
  margin:5px 8px 7px 0;
  color:#444;
  font-weight: bold;
  display: inline-block;
}
.tmsg-c-item article header .i-class{
  display: inline-block;
  margin-left:10px;
  background: #dff0d8;
  color:#3c763d;
  border-radius: 5px;
  padding: 3px 6px;
  font-size: 12px;
  font-weight: 400;
}
.tmsg-c-item article header .i-time{
  color:#aaa;
  font-size: 12px;
}
.tmsg-c-item article section{
  margin-left: 80px;
}
.tmsg-c-item article section p img{
  vertical-align: middle;
}
.tmsg-c-item article section .tmsg-replay{
  margin:10px 0;
  font-size: 12px;
  color:#64609E;
  cursor: pointer;
}

</style>
