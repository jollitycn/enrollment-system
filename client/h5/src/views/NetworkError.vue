
<template>
    <div>
        <h5-header :leftStatus="true" title="网络错误"></h5-header>
        <custom-empty :type="2" height="calc(100vh - 100px)" />
    </div>
 
</template>

<script >
import Header from "@/components/cheader";
import CustomEmpty from "@/components/custom-empty";


export default {
  name: "NetworkError",
  components: {
    "h5-header": Header,
    "custom-empty":CustomEmpty
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped lang="stylus">
.custom-image {
    >>>.van-empty__image {
        width: 187px;
        height: 144px;
    }
}

</style>
