<template>
  <header class="display-i-g header-container shadow-normal">header</header>
  <Link :option="{ to: 'aaa', name: '링크' }" />
</template>

<script lang="ts">
  import { defineComponent } from 'vue';
  import Link from '@/components/atoms/link/Link.vue';

  export default defineComponent({
    name: 'ContentHeader',
    components: { Link },
  });
</script>

<style scoped></style>
