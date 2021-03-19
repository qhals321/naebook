<template>
  <img :src="imageUrl" :alt="alt" />
</template>

<script lang="ts">
  import { defineComponent } from 'vue';

  export default defineComponent({
    name: 'Avartar',
    components: {},
    props: {
      imageUrl: {
        type: String,
        required: true,
      },
      alt: {
        type: String,
        requrie: false,
        default: '프로필 이미지',
      },
    },
  });
</script>
