<template>
  <img class="avatar-image" :src="imageUrl" :alt="alt" />
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
<style scoped>
  .avatar-image {
    width: 100px;
    height: 100px;
    border-radius: 100%;
  }
</style>
