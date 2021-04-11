<template>
  <content-header />
</template>

<script lang="ts">
  import { defineComponent } from 'vue';
  import ContentHeader from '@/components/organisms/Header/Header.vue';
  export default defineComponent({
    name: 'PageHeader',
    components: { ContentHeader },
  });
</script>
