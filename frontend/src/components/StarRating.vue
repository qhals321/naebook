<template>
  <div>
    <template v-for="idx in 5">
      <star
        :key="idx"
        :current-rating.sync="currentRating"
        :unique-key="idx"
        :selected-rating="selectedRatingSync"
        @select-rating="setSelectedRating"
      />
    </template>
  </div>
</template>

<script lang="ts">
  import { Vue, Component, PropSync } from 'vue-property-decorator';
  import Star from '@/components/Star.vue';
  @Component({
    components: { Star },
  })
  export default class StarRating extends Vue {
    @PropSync('selectedRating')
    private selectedRatingSync!: number;

    private currentRating = 0;

    private setSelectedRating(): void {
      this.selectedRatingSync = this.currentRating;
    }

    private setCurrentRating(): void {
      this.currentRating = this.selectedRatingSync;
    }

    private created(): void {
      this.setCurrentRating();
    }
  }
</script>
