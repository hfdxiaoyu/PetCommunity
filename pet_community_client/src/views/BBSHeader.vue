<template>
  <div>
    <div class="navbar-box" style="min-height: 48px;">
      <!-- fixed定位后的导航栏 -->
      <div class="navbar">
        <!-- 导航栏容器 -->
        <div class="navbar-container">
          <!-- 左侧 -->
          <div class="navbar-container-left">

            <!-- 网页logo -->
            <div class="logo" @click="transIndex">
              <img src="../assets/logo.png">
            </div>

            <!-- 左侧导航栏 -->
            <ul class="left-ul">
              <li title="">
                <router-link active-class="active" to="/"> 首页</router-link>
              </li>
              <li title="">
                <router-link active-class="active" to="/forum"> 宠物相亲</router-link>
              </li>
              <li title="">
                <router-link active-class="active" to="/community"> 宠物社交</router-link>
              </li>
              <li title="">
                <router-link active-class="active" to="/inventory"> 宠物代管</router-link>
              </li>

              <li title="">
                <router-link active-class="active" to="/inventory"> 代遛</router-link>
              </li>

              <li title="">
                <router-link active-class="active" to="/inventory"> 互助</router-link>
              </li>
            </ul>

          </div>

          <!-- 搜索框 -->
          <div class="navbar-container-middle">
            <div class="navbar-search-container">
              <input type="text" autocomplete="off" id="search" v-model="keywords">
              <button @click="search">
                <i></i>
                <span>搜索</span>
              </button>
            </div>
          </div>

          <!-- 右侧 -->
          <div class="navbar-container-right">
            <div class="navbar-btns-User">
              <!--用户登录: 展示用户的头像以及其他信息-->
              <!--    移到用户头像上面，信息弹出框   -->
              <div class="userPhoto" @mouseover="mouseOver" @mouseleave="mouseLeave" v-if="getIsLogin">
                <!--                <el-popover-->
                <!--                  placement="top"-->
                <!--                  width="150"-->
                <!--                  trigger="hover">-->
                <!--                  弹出页面-->
                <!--                  <div style="display: flex;flex-direction: column; align-items: center;">-->
                <!--                    <a href="#">个人中心</a>-->
                <!--                    <a href="#" @click="logout">退出登录</a>-->
                <!--                  </div>-->

                <!-- 用户头像 -->
                <a class="hasAvatar" :style="{opacity}" href="#" slot="reference">
                  <img :src="getUserImg">
                </a>
                <!--                </el-popover>-->


                <!-- 用信息弹出框 初始display:none-->
                <div class="navbar-profile" :style="{display}" v-if="user">
                  <div class="profile-user">
                    <!-- 用户简介的头像 -->
                    <a class="profile-avatar" href="#">
                      <img :src="getUserImg">
                    </a>
                    <p class="profile-nickName">{{ this.user.nickName }}</p>
                  </div>
                  <!--                  <div class="profile-fans">-->
                  <!--                    <ul class="">-->
                  <!--                      <li><p>{{ user.fans }}</p>粉丝</li>-->
                  <!--                      <li><p>{{ user.attention }}</p>关注</li>-->
                  <!--                      <li><p>{{ user.good }}</p>获赞</li>-->
                  <!--                    </ul>-->
                  <!--                  </div>-->
                  <div class="profile-personal">
                    <ul>
                      <li @click="userinfo">个人中心</li>
                      <li>账号设置</li>
                    </ul>
                  </div>
                  <div class="profile-logout" @click="myCollection">我的收藏</div>
                  <div class="profile-logout" @click="logout">退出登录</div>
                </div>
              </div>
              <!--用户未登录: 提示用户登录-->
              <div class="userPhoto" v-else @click.prevent="tologin">

                <el-dialog title="登录" :width="'40%'" :visible.sync="dialogFormVisible"
                           :close-on-click-modal="true" :destroy-on-close="true"
                           :modal-append-to-body="false">
                  <el-form :model="form" :rules="rules" ref="userForm">
                    <el-form-item label="账号" prop="userName" :label-width="formLabelWidth">
                      <el-input v-model="form.userName" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="密码" prop="password" :label-width="formLabelWidth">
                      <el-input v-model="form.password" type="password" autocomplete="off"></el-input>
                    </el-form-item>
                  </el-form>
                  <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible = false;regDialogFormVisible=true;">注 册</el-button>
                    <el-button type="primary" @click="login">登 录</el-button>
                  </div>
                </el-dialog>

                <a href="#" v-if="!getIsLogin" type="text" @click="dialogFormVisible = true">登录/注册</a>
                <!--  注册-->
                <el-dialog title="注册" :width="'40%'" :visible.sync="regDialogFormVisible"
                           :close-on-click-modal="true" :destroy-on-close="true"
                           :modal-append-to-body="false">
                  <el-form :model="reform" :rules="reRules" ref="reUserForm">
                    <el-form-item label="账号" prop="userName" :label-width="formLabelWidth">
                      <el-input v-model="reform.userName" autocomplete="off"></el-input>
                    </el-form-item>

                    <el-form-item label="昵称" prop="nickName" :label-width="formLabelWidth">
                      <el-input v-model="reform.nickName" type="text" autocomplete="off"></el-input>
                    </el-form-item>

                    <el-form-item label="密码" prop="password" :label-width="formLabelWidth">
                      <el-input v-model="reform.password" type="password" autocomplete="off"></el-input>
                    </el-form-item>

                    <el-form-item label="邮箱" prop="email" :label-width="formLabelWidth">
                      <el-input v-model="reform.email" type="text" autocomplete="off"></el-input>
                    </el-form-item>

                    <el-form-item label="电话号码" prop="phonenumber" :label-width="formLabelWidth">
                      <el-input v-model="reform.phonenumber" type="text" autocomplete="off"></el-input>
                    </el-form-item>

                    <el-form-item label="地址" prop="address" :label-width="formLabelWidth">
                      <el-input v-model="reform.address" type="text" autocomplete="off"></el-input>
                    </el-form-item>

                    <el-form-item label="性别" prop="sex" :label-width="formLabelWidth">
                      <el-radio-group v-model="reform.sex">
                        <el-radio label="1" value="1">男</el-radio>
                        <el-radio label="2" value="2">女</el-radio>
                        <el-radio label="0" value="0">未知</el-radio>
                      </el-radio-group>
                    </el-form-item>

<!--                    <el-form-item label="头像" prop="avatar" :label-width="formLabelWidth" style="display: none;">-->
                      <!--                      头像上传-->
                      <!--                  <el-upload-->
                      <!--                    class="avatar-uploader"-->
                      <!--                    action="http://localhost:9090/#/file/upload"-->
                      <!--                    :show-file-list="false"-->
                      <!--                    :on-success="handleAvatarSuccess"-->
                      <!--                    :before-upload="beforeAvatarUpload">-->
                      <!--                    <img v-if="imageUrl" :src="imageUrl" class="avatar">-->
                      <!--                    <i v-else class="el-icon-plus avatar-uploader-icon"></i>-->
                      <!--                  </el-upload>-->

<!--                      <el-input v-model="reform.avatar" value="http://localhost:9090/file/getfile/58139dcbf34c47eda646ef67b0ca33b7.jpg" type="text" autocomplete="off"></el-input>-->
<!--                    </el-form-item>-->


                  </el-form>
                  <div slot="footer" class="dialog-footer">
                    <el-button @click="regDialogFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="registe">提 交</el-button>
                  </div>
                </el-dialog>

              </div>
              <!--               头像-->
              <!--              <div class="navbar-btn navbar-btn-dynamic navbar-fl" v-if="getIsLogin">-->

              <!--                <div class="demo-image">-->
              <!--                  <div class="block" v-for="fit in fits" :key="fit">-->
              <!--                    <el-image-->
              <!--                      style="width: 45px; height: 45px; border-radius: 50%"-->
              <!--                      :src="getUserImg"-->
              <!--                      :fit="fit"></el-image>-->
              <!--                  </div>-->
              <!--                </div>-->
              <!--              </div>-->

              <!-- 动态 -->
              <div class="navbar-btn navbar-btn-dynamic navbar-fl" @click.prevent="stat">
                <a href="">动态</a>
              </div>
              <!-- 消息 -->
              <div class="navbar-btn navbar-btn-news navbar-fl" @click.prevent="information">
                <a href="">消息</a>
              </div>

              <!-- 发布 -->
              <div class="navbar-btn navbar-btn-write navbar-fl">
                <a class="write" @click="write">
                  <i></i>发布
                  <i></i></a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!--    宠物标签栏-->
    <div style="min-height: 48px;display: flex;flex-direction: row;">
      <div style="width: 15%"></div>
      <div>
        我是宠物标签
      </div>
      <div style="width: 15%"></div>
    </div>
    <div>
      <div>
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "BBSHeader",
  data() {
    return {
      opacity: 1,
      display: 'none',
      user: JSON.parse(localStorage.getItem('user')),
      keywords: '',
      dialogFormVisible: false,//表单
      regDialogFormVisible: false,//注册表单
      form: { //登录表单
        userName: '',
        password: '',
      },
      reform: {//注册表单
        userName: '',
        nickName: '',
        password: '',
        email: '',
        phonenumber:'',
        address:'',
        sex: '0',
        avatar:'http://localhost:9090/file/getfile/58139dcbf34c47eda646ef67b0ca33b7.jpg'
      },
      rules: {//登录表单验证
        userName: [
          {required: true, message: '请输入用户名', trigger: 'blur'},
          {min: 5, max: 15, message: '长度在5到15个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 5, max: 20, message: '长度在5到20个字符', trigger: 'blur'}
        ],
      },
      reRules: { //注册表单验证

      },
      formLabelWidth: '120px',
      imageUrl: '', //上传头像url
    }
  },
  methods: {
    mouseOver() {
      this.opacity = 0
      this.display = 'block'
    },
    mouseLeave() {
      this.opacity = 1
      this.display = 'none'
    },
    transIndex() {
      this.$router.push('/forum')
    },
    tologin() {
      // this.$router.push('/login')
    },
    write() {
      this.$router.push('/write')
    },
    userinfo() {
      this.$router.push('/tabs/' + '1')
    },
    myCollection() {
      this.$router.push('/collection')
    },
    search() {
      const keywords = this.keywords;
      if (keywords !== '') {
        this.$router.push({path: `/search/keywords/${keywords}`})
        this.keywords = ''
      }
    },
    stat() {
      this.$router.push('/stat')
    },
    information() {
      this.$router.push('/information')
    },//我的方法
    login() {//登录的方法
      this.$refs['userForm'].validate((valid) => {
        if (valid) { //表单校验合法
          this.request.post("/user/login", this.form).then(res => {
            if (res.data.code == '200') {
              localStorage.setItem("user", JSON.stringify(res.data.data)) //存储token信息到浏览器
              this.$message.success("登录成功")
              this.$store.state.isLogin = true //设置登录成功标志
              //把头像存入vuex
              this.$store.state.userimg = res.data.data.avatar
              this.$router.push("/")
            } else {
              this.$message.error(res.data.msg)
            }
          })
          this.dialogFormVisible = false
        }
      })

    },//退出登录
    logout() {
      this.request.post("/user/logout").then(res => {
        console.log(res)
        //清空所有缓存
        window.sessionStorage.clear()
        this.$store.state.isLogin = false;
        this.$store.state.userimg = '';
        //移除user对象
        localStorage.removeItem("user")
        this.$message.success("退出登录成功")
      })
      // location.reload()       //刷新页面
    },
    registe() { //注册的方法
      console.log(this.reform)

      this.request.post("/user/register", this.reform).then(res => {
        if (res.data.code == '200') {
          this.$message.success("注册成功")
          this.reform={//初始化注册表单
            userName: '',
            nickName: '',
            password: '',
            email: '',
            phonenumber:'',
            address:'',
            sex: '0',
            avatar:'http://localhost:9090/file/getfile/58139dcbf34c47eda646ef67b0ca33b7.jpg'
          }
          this.regDialogFormVisible=false
          this.$router.push("/")
        } else {
          this.$message.error(res.data.msg)
        }
      })

    },
    getByIsLogin() { //判断是否登录
      let user = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : null
      if (user == null) {
        this.$store.state.isLogin = false
        this.$store.state.userimg = ''
      } else {
        this.$store.state.isLogin = true
        this.$store.state.userimg = user.avatar
      }
    },//头像上传的方法
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    },

  },//计算属性
  computed: {
    getUserImg() { //获取用户头像的计算属性
      return this.$store.state.userimg
    },
    getIsLogin() { //获取是否登录
      return this.$store.state.isLogin
    }
  },
  created() {
    this.getByIsLogin()
  }
}
</script>

<style scoped>
.navbar {
  position: fixed;
  z-index: 2001;
  top: 0px;
  width: 100%;
  left: 0px;
  font-size: 14px;
  /* 字体粗细，400相当于normal */
  font-weight: 400;
  color: #222226;
  background-color: #fff;
  /*background-color: #292c2f;*/
  /* 阴影  水平阴影距离，垂直阴影距离， 模糊尺寸， 阴影尺寸 颜色*/
  box-shadow: 0 2px 4px 0 rgb(0, 0, 0, 5%);
}

.navbar-container {
  width: 100%;
  /* 最小宽度，窗口缩小之后到1280px不会再缩小 */
  min-width: 1280px;
  box-sizing: border-box;
  padding: 0 24px;
  margin: 0 auto;
  height: 48px;
  line-height: 48px;
  display: flex;
  justify-content: space-between;
  -webkit-box-pack: justify;
}

.toolbar-container-left {
  /* 元素会根据自身宽高来设置尺寸。它是完全非弹性的：既不会缩短，也不会伸长来适应 flex 容器 */
  flex: none;
}

.logo {
  position: relative;
  float: left;
  margin-right: 8px;
  /* 当箭头鼠标移到会变成手式鼠标 */
  cursor: pointer;
}

.logo img {
  width: 80px;
  max-width: 80px;
  height: 44px;
  display: block;
  /* cacl()将img放在盒内的高的中间 */
  margin-top: calc((48px - 44px) / 2);
}

.left-ul {
  width: auto;
  height: 100%;
  /* 去除li中的间隙，在li中要设置font-size,否则没有内容 */
  font-size: 0px;
  float: left;
}

.left-ul li {
  position: relative;
  display: inline-block;
  font-size: 14px;
  height: 100%;
  line-height: 48px;
}

.left-ul li a {
  color: #000;
  padding: 0 20px;
  text-decoration: none;
  font: 16px Arial, Helvetica, sans-serif;
  /*font: normal 28px Cookie, Arial, Helvetica, sans-serif;*/
  opacity: 0.9;
}

/* 鼠标停留时的样式 */
.left-ul li:hover {
  background-color: #eee;
}

.left-ul li a:hover {
  opacity: 1;
}

/*选中的样式*/
.left-ul li a.active {
  color: skyblue;
  pointer-events: none;
  opacity: 1;
}

a {
  color: #000;
  padding: 0 10px;
  font: 16px Arial, Helvetica, sans-serif;
  /* 删除a标签的下划线 */
  text-decoration: none;
}

.navbar-container-middle {
  padding: 0 40px;
  flex: 1;
}

.navbar-search-container {
  width: 100%;
  max-width: 720px;
  height: 32px;
  line-height: 32px;
  margin-top: calc((48px - 32px) / 2);
  box-sizing: border-box;
  font-size: 0px;
  margin-left: auto;
  margin-right: auto;

}

.navbar-search-container input {
  font-size: 14px;
  display: inline-block;
  width: calc(100% - 88px);
  height: 100%;
  line-height: inherit;
  /* 为了后面聚焦搜索框样式，将边框外围线清除 */
  outline: 0;
  background: #f5f6f7;
  color: #222226;
  vertical-align: top;
  text-indent: 16px;
  border: 1px solid #e8e8ed;
  border-right: none;
  box-sizing: border-box;
  border-radius: 16px 0 0 16px;
}

/* 搜索框聚焦样式 */
.navbar-search-container input:focus {
  border: 1px solid skyblue;
  border-right: none;
}

.navbar-search-container button {
  display: inline-block;
  width: 88px;
  height: 100%;
  outline: 0;
  border: 0 none;
  border-radius: 0 16px 16px 0;
  font-size: 14px;
  line-height: 32px;
  cursor: pointer;
  background-color: skyblue;
  text-align: left;
}

.navbar-search-container i {
  display: inline-block;
  width: 24px;
  height: 24px;
  background: url(https://g.csdnimg.cn/common/csdn-toolbar/images/csdn-white-search.png) no-repeat center center;
  background-size: 100%;
  vertical-align: middle;
  position: relative;
  top: -1px;
  margin-left: 14px;
}

.navbar-search-container span {
  display: inline-block;
  vertical-align: top;
  color: #fff;
}

.navbar-btns-User {
  flex: 1;
}

.profile-fans {
  display: flex;
  flex-direction: column;
  border-bottom: 1px solid #e8e8ed;
}

.profile-fans ul {
  display: flex;
  flex-direction: row;
  width: 100%;
  margin: 4px 0;
}

.profile-fans ul li {
  position: relative;
  display: inline-block;
  width: 33.333%;
  line-height: 26px;
}

.profile-fans ul li:hover {
  cursor: pointer;
}

.profile-fans ul li p {
  font-size: large;
}

.profile-personal {
  border-bottom: 1px solid #e8e8ed;
}

.profile-personal ul {
  width: 100%;
  line-height: 30px;
  margin: 10px 0px;
}

.profile-personal ul li {
  position: relative;
  display: inline-block;
  width: 50%;
}

.profile-personal ul li:hover {
  cursor: pointer;
  background-color: #eee;
}

.profile-logout:hover {
  cursor: pointer;
  background-color: #eee;
}

.userPhoto {
  height: 100%;
  float: left;
  position: relative;
  line-height: 48px;
  text-align: center;
  padding: 0 8px;
}

.hasAvatar {
  display: block;
  line-height: 48px;
  color: #222226;
  margin-top: calc((48px - 32px) / 2);
  margin-right: 16px;
  opacity: 1;
}

.hasAvatar img {
  width: 32px;
  height: 32px;
  border-radius: 50%;
}

.navbar-profile {
  width: 248px;
  color: #222226;
  background: #fff;
  position: absolute;
  min-height: 200px;
  top: 48px;
  left: 50%;
  margin-left: -132px;
  z-index: 9999999;
  border-radius: 4px;
  box-shadow: 0 0 10px 2px rgb(0 0 0 / 6%);
}

.navbar-profile .profile-user {
  text-align: center;
  padding: 20px 0 12px 0;
  border-bottom: 1px solid #e8e8ed;
}

.navbar-profile .profile-user .profile-avatar {
  position: absolute;
  width: 48px;
  height: 48px;
  padding: 0px;
  top: -32px;
  left: 50%;
  -webkit-transform: translate(-50%, 0);
  transform: translate(-50%, 0);
  border-radius: 50%;
  cursor: pointer;
  z-index: 9999;
  border: 1px solid #e8e8ed;
}

.profile-user .profile-avatar img {
  width: 100%;
  height: 100%;
  border-radius: 50%;
}

.navbar-profile .profile-user .profile-nickName {
  width: 100%;
  box-sizing: border-box;
  padding: 0 16px;
  font-size: 16px;
  color: #222226;
  font-weight: 500;
  height: 40px;
  line-height: 40px;
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
}

.navbar-btn {
  position: relative;
  height: 48px;
  line-height: 48px;
  color: inherit;
  text-align: center;
  padding: 0 8px;
}

.navbar-fl {
  float: left;
}

.navbar-btn-write > a i:first-child {
  display: inline-block;
  width: 20px;
  height: 20px;
  vertical-align: middle;
  /*background: url(https://img-home.csdnimg.cn/images/20201218055848.png) no-repeat -38px -20px;*/
  background: url("../assets/write.png");
  background-size: 100%;
  margin-right: 2px;
}

.navbar-btn-write > a i:last-child {
  display: inline-block;
  background: 0 0;
  width: 10px;
  height: 7px;
  margin-left: 7px;
  vertical-align: middle;
  background: url(https://g.csdnimg.cn/common/csdn-toolbar/images/write-hover-thro.png) no-repeat center center;
  background-size: 100%;
}

.write {
  display: block;
  min-width: 88px;
  height: 32px;
  line-height: 32px;
  text-align: center;
  color: #fff;
  background: skyblue;
  border-radius: 20px;
  margin-top: calc((48px - 32px) / 2);
}

.write:hover {
  cursor: pointer;
}

/*头像上传*/
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
