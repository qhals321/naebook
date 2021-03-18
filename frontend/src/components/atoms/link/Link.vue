<template>
  <router-link
    v-if="option.type === 'link'"
    :class="option.class"
    :to="option.path"
    >{{ option.label }}</router-link
  >
  <a v-else :class="option.class" :href="option.path">{{ option.label }}</a>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';
  import { LinkOption } from '@/types/atoms';

  export default defineComponent({
    name: 'Link',
    props: {
      option: LinkOption,
    },
  });
</script>

<style scoped>
  a {
    /* TODO: 임시코드 */
    display: inline-block;
    background-color: red;
  }

  .router-link-active {
    /* TODO: 임시코드 */
    background-color: blue;
  }
</style>
