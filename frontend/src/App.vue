<template>
  <div>
    <content-header />
    <content-nav />
    <content-main />
    <!--    <content-footer />-->
  </div>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';
  import ContentHeader from '@/views/pages/header/PageHeader.vue';
  import ContentNav from '@/views/pages/nav/ContentNav.vue';
  import ContentMain from '@/views/pages/main/ContentMain.vue';

  export default defineComponent({
    name: 'App',
    components: {
      ContentHeader,
      ContentNav,
      ContentMain,
    },
  });
</script>
