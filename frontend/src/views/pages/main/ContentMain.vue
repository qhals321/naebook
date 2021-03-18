<template>
  <main class="content">
    <influencer-section />
  </main>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';
  import InfluencerSection from '@/components/organisms/InfluencerSection/InfluencerSection.vue';

  export default defineComponent({
    name: 'ContentMain',
    components: { InfluencerSection },
  });
</script>

<style scoped>
  .content {
    margin: 0 auto;
    max-width: 1024px;
  }
</style>
