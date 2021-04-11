<template>
  <div>
    <content-header />
    <page-nav />
    <content-main />
    <!--    <content-footer />-->
  </div>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';
  import ContentHeader from '@/views/pages/header/ContentHeader.vue';
  import PageNav from '@/views/pages/nav/PageNav.vue';
  import ContentMain from '@/views/pages/main/ContentMain.vue';

  export default defineComponent({
    name: 'App',
    components: {
      ContentHeader,
      PageNav,
      ContentMain,
    },
  });
</script>
