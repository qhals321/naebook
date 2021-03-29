<template>
  <router-link :to="path"
    ><slot>{{ name }}</slot></router-link
  >
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
    cursor: pointer;
    font-family: var(--font-style-gaegu);
    /*font-size: var(--font-title-regular);*/
    font-weight: var(--weight-bold);
    display: inline-block;
    color: var(--color-dark-gray);
  }

  .router-link-exact-active {
    color: var(--color-black);
  }

  /*.menu-link {*/
  /*  display: inline-flex;*/
  /*  justify-content: center;*/
  /*  align-items: center;*/
  /*}*/

  /*.menu-name {*/
  /*  font-size: var(--font-title-micro);*/
  /*}*/
</style>
