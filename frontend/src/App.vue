<template>
  <div class="ly-container">
    <content-header />
    <content-nav />
    <body-content />
    <footer id="footer"></footer>
  </div>
</template>

<script lang="ts">
  import { Vue, Component } from 'vue-property-decorator';
  import ContentHeader from '@/views/main/header/TopContent.vue';
  import BodyContent from '@/views/main/body/BodyContent.vue';
  import ContentNav from '@/views/main/nav/ContentNav.vue';
  @Component({
    components: { ContentNav, BodyContent, ContentHeader },
  })
  export default class App extends Vue {}
</script>
