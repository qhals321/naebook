<template>
  <div>
    <details-link
      v-for="(option, idx) in linkOptions"
      :key="idx"
      :link-option="option"
    />
  </div>
</template>

<script lang="ts">
  import { defineComponent, PropType } from 'vue';
  import Link from '@/components/atoms/link/Link.vue';
  import { LinkOption } from '@/types/atoms';

  export default defineComponent({
    name: 'Navigator',
    components: {
      DetailsLink: Link,
    },
    props: {
      linkOptions: {
        type: Object as PropType<LinkOption[]>,
      },
    },
  });
</script>

<style scoped>
  div {
    display: flex;
    flex-direction: row;
  }
</style>
