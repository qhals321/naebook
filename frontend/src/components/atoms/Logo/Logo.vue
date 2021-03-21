<template>
  <Link :option="logoOption" />
</template>

<script lang="ts">
  import { defineComponent } from 'vue';
  import Link from '@/components/atoms/link/Link.vue';
  import { LinkOption } from '@/types/atoms';

  const logoOption: LinkOption = {
    type: 'link',
    class: 'Logo',
    path: '/',
    label: 'NaeBook',
  };

  export default defineComponent({
    name: 'Logo',
    components: { Link },
    setup() {
      return {
        logoOption,
      };
    },
  });
</script>

<style scoped></style>
