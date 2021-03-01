<template>
  <div @dragstart="startDrag(book, $event)">
    <h1>{{ book.isbn }}</h1>
    <p>{{ book.access }}</p>
    <p>{{ book.status }}</p>
  </div>
</template>

<script lang="ts">
  import { Vue, Component, Prop } from 'vue-property-decorator';
  import { Book, DataTransferType } from '@/components/dragAndDrop/Board.vue';
  @Component
  export default class CardItem extends Vue {
    @Prop()
    private readonly book!: Book;

    private startDrag(book: Book, { dataTransfer }: DataTransferType) {
      this.$emit('startDrag', { book, dataTransfer });
    }
  }
</script>
