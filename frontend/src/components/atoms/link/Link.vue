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
  // import { LinkOption } from '@/types/atoms';

  export default defineComponent({
    name: 'Link',
    props: ['option'],
    // TODO: LinkOption only refers to a type, but is being used as a value here.
    // props: {
    //   option: LinkOption,
    // },
  });
</script>

<style scoped>
  a {
    font-family: var(--font-style-gaegu);
    font-size: var(--font-title-regular);
    font-weight: var(--weight-bold);
    display: inline-block;
    color: var(--color-dark-gray);
  }

  .router-link-exact-active {
    color: var(--color-black);
  }

  .logo {
    font-size: var(--font-logo);
    color: var(--color-white);
  }
</style>
