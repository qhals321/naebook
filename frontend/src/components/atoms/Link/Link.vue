<template>
  <router-link :to="path">{{ name }}</router-link>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';

  export default defineComponent({
    name: 'Link',
    props: {
      path: {
        type: String,
        required: false,
        default: '/',
      },
      name: {
        type: String,
        required: true,
      },
    },
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

  /* TODO 사용부로 옮기기 */
  .logo {
    font-size: var(--font-logo);
    color: var(--color-white);
  }
</style>
