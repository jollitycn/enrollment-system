<template>
  <div>
    <div class="printTest">
      <div id="print">
        <div>
          <div>
            <vue-qr v-for="n in 10"
                    :key="n"
                    :logoSrc="config.logo"
                    :text="config.value"
                    :size="200"
                    :margin="15"></vue-qr>
          </div>
          <blockquote></blockquote>
          <div>
            <vue-qr v-for="n in 10"
                    :key="n+10"
                    :logoSrc="config.logo"
                    :text="config.value"
                    :size="200"
                    :margin="15"></vue-qr>
          </div>
        </div>
      </div>
    </div>
    <button @click="printAll">打印</button>
  </div>
</template>

<script>
    import VueQr from 'vue-qr'
    export default {
        name: 'Print',
        data () {
            return {
                config: {
                    value: '231312312312',
                    logo: ''
                }
            }
        },
        components:{
            VueQr
        },
        methods :{
            printAll() {
                let head_str = "<html><head><title></title></head><body>"; //先生成头部
                let foot_str = "</body></html>"; //生成尾部
                let older = document.body.innerHTML;
                let new_str = document.getElementById('print').innerHTML; //获取指定打印区域
                document.body.innerHTML = head_str + new_str + foot_str; //构建新网页
                window.print()
                document.body.innerHTML = older; //将网页还原
                return false;
            }
        }
    };
</script>

<style scoped>
  .printTest{

  }
</style>
