<template>
  <router-link :to="linkToOption">{{ linkToOption.name }}</router-link>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';

  export default defineComponent({
    name: 'Link',
    props: ['linkToOption'],
  });
</script>
