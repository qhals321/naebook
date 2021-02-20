<template>
  <div>
    <content-header />
    <body-content />
    <footer id="footer"></footer>
  </div>
</template>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';
import ContentHeader from '@/views/main/header/TopContent.vue';
import BodyContent from '@/views/main/body/BodyContent.vue';
@Component({
  components: { BodyContent, ContentHeader }
})
export default class App extends Vue {}
</script>
