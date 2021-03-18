<template>
  <section>
    <h2>인플루엔서 추천</h2>
    <influencer-list />
  </section>
</template>

<script lang="ts">
  import { defineComponent } from 'vue';
  import InfluencerList from '@/components/organisms/InfluencerSection/InfluencerList.vue';

  export default defineComponent({
    name: 'InfluencerSection',
    components: { InfluencerList },
  });
</script>
