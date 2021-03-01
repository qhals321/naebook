<template>
  <div class="ly-container">
    <content-header />
    <content-nav />
    <library />
    <footer id="footer"></footer>
  </div>
</template>

<script lang="ts">
  import { Vue, Component } from 'vue-property-decorator';
  import ContentHeader from '@/views/main/header/TopContent.vue';
  import ContentNav from '@/views/main/nav/ContentNav.vue';
  import Library from '@/views/library/Library.vue';
  @Component({
    components: { Library, ContentNav, ContentHeader },
  })
  export default class App extends Vue {}
</script>
