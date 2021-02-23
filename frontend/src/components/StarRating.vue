<template>
  <div>
    <template v-for="idx in 5">
      <star
        :key="idx"
        :current-rating.sync="currentRating"
        :unique-key="idx"
        :selected-rating="selectedRating"
        @select-rating="selectRating"
      />
    </template>
  </div>
</template>

<script lang="ts">
  import { Vue, Component } from 'vue-property-decorator';
  import Star from '@/components/Star.vue';
  @Component({
    components: { Star },
  })
  export default class StarRating extends Vue {
    private currentRating = 0;
    private selectedRating = 0;

    private selectRating() {
      this.selectedRating = this.currentRating;
    }
  }
</script>
