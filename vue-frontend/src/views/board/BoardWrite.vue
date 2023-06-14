<template>
    <div class="board-detail">
      <div class="common-buttons">
        <button type="button" class="w3-button w3-round w3-blue-gray" v-on:click="fnSave">저장</button>&nbsp;
        <button type="button" class="w3-button w3-round w3-gray" v-on:click="fnList">목록</button>
      </div>
      <div class="board-contents">
        <input type="text" v-model="title" class="w3-input w3-border" placeholder="제목을 입력해주세요.">
        <input type="text" v-model="reg_nm" class="w3-input w3-border" placeholder="작성자를 입력해주세요." v-if="brd_no === undefined">
      </div>
      <div class="board-contents">
        <textarea id="" cols="30" rows="10" v-model="contents" class="w3-input w3-border" style="resize: none;">
        </textarea>
      </div>
      <div class="common-buttons">
        <button type="button" class="w3-button w3-round w3-blue-gray" v-on:click="fnSave">저장</button>&nbsp;
        <button type="button" class="w3-button w3-round w3-gray" v-on:click="fnList">목록</button>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    data() { //변수생성
      return {
        requestBody: this.$route.query,
        brd_no: this.$route.query.brd_no,
  
        title: '',
        reg_nm: '',
        contents: '',
        reg_dt: ''
      }
    },
    mounted() {
      this.fnGetView()
    },
    methods: {
      fnGetView() {
        if (this.brd_no !== undefined) {
          this.$axios.get(this.$serverUrl + '/board/' + this.brd_no, {
            params: this.requestBody
          }).then((res) => {
            this.title = res.data.title
            this.reg_nm = res.data.reg_nm
            this.contents = res.data.contents
            this.reg_dt = res.data.reg_dt
          }).catch((err) => {
            console.log(err)
          })
        }
      },
      fnList() {
        delete this.requestBody.brd_no
        this.$router.push({
          path: './list',
          query: this.requestBody
        })
      },
      fnView(brd_no) {
        this.requestBody.brd_no = brd_no
        this.$router.push({
          path: './detail',
          query: this.requestBody
        })
      },
      fnSave() {
        let apiUrl = this.$serverUrl + '/board'
        this.form = {
          "brd_no": this.brd_no,
          "title": this.title,
          "contents": this.contents,
          "reg_nm": this.reg_nm
        }
  
        if (this.brd_no === undefined) {
          //INSERT
          this.$axios.post(apiUrl, this.form)
          .then((res) => {
            alert('글이 저장되었습니다.')
            this.fnView(res.data.brd_no)
          }).catch((err) => {
            if (err.message.indexOf('Network Error') > -1) {
              alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
            }
          })
        } else {
          //UPDATE
          this.$axios.patch(apiUrl, this.form)
          .then((res) => {
            alert('글이 저장되었습니다.')
            this.fnView(res.data.brd_no)
          }).catch((err) => {
            if (err.message.indexOf('Network Error') > -1) {
              alert('네트워크가 원활하지 않습니다.\n잠시 후 다시 시도해주세요.')
            }
          })
        }
      }
    }
  }
  </script>
  <style scoped>
  
  </style>