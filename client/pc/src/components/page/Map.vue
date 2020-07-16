<template>
  <div>
    <baidu-map :style="{width: map.mapWidth + '%',height: map.mapHeight + 'px'}"
               :center="map.center"
               :zoom="map.zoom"
               :scroll-wheel-zoom="map.scrollWheelZoom"
               @resize="resizeMap"
    >
    </baidu-map>
  </div>
</template>

<script>
    export default {
        name: 'Map',
        data () {
            return {
                map: {
                    mapWidth: 100,
                    mapHeight: document.body.clientHeight - 130,
                    center: {lng: 114.061677, lat: 22.54899},
                    zoom: 12,
                    scrollWheelZoom: true
                }
            }
        },
        methods:{
            resizeMap (enent) {
                this.map.mapWidth = 100
                this.map.mapHeight = document.body.clientHeight - 130
            }
        }
    };
</script>

<style scoped>
  .map {
    width: 100%;
    height: 100%;
  }
</style>
