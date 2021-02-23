<template>
  <div
    class="outline"
    @mouseenter="mouseMove('fill')"
    @mouseleave="mouseMove('outline')"
    @click="selectRating"
  >
    <span>
      <span
        class="iconify star"
        data-icon="ant-design:star-outlined"
        data-inline="false"
      ></span>
    </span>
    <span :class="['fill', { hide: !colorable }]">
      <span
        class="iconify star"
        data-icon="ant-design:star-filled"
        data-inline="false"
      ></span>
    </span>
  </div>
</template>

<script lang="ts">
  import { Vue, Component, Prop, PropSync } from 'vue-property-decorator';
  import { StarStyleType } from '@/types/common';
  @Component
  export default class Star extends Vue {
    @PropSync('currentRating')
    private currentRatingSync!: number;
    @Prop()
    private readonly uniqueKey!: number;
    @Prop()
    private readonly selectedRating!: number;

    private mouseMove(type: StarStyleType): void {
      type === 'fill' ? this.setCurrentRating() : this.resetRating();
    }

    private setCurrentRating(): void {
      this.currentRatingSync = this.uniqueKey;
    }

    private resetRating(): void {
      this.currentRatingSync = this.selectedRating;
    }

    private get colorable(): boolean {
      return (
        this.currentRatingSync !== 0 && this.currentRatingSync >= this.uniqueKey
      );
    }

    private selectRating(): void {
      this.$emit('select-rating');
    }
  }
</script>
<style scoped>
  >>> .star > path {
    color: gold;
  }
  .outline {
    display: inline-block;
    position: relative;
  }
  .fill {
    position: absolute;
    left: 0;
  }
  .hide {
    display: none;
  }
</style>
