<template>
  <div class="card" :style="style">
    <slot></slot>
  </div>
</template>

<script lang="ts">
  import { computed, defineComponent } from 'vue';

  export default defineComponent({
    name: 'Card',
    props: {
      backgroundColor: {
        type: String,
        require: false,
        default: '#fff',
      },
    },

    setup(props) {
      const style = computed(() => {
        const { backgroundColor } = props;
        return {
          'background-color': backgroundColor,
        };
      });

      return {
        style,
      };
    },
  });
</script>
<style scoped>
  .card {
    margin: 0;
    width: 309px;
    /* height: 200px; */
    border-radius: 16px;
    box-shadow: 0px 4px 4px rgba(0, 0, 0, 0.25);
  }
</style>
