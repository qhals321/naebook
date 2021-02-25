<template>
  <div @drop="onDrop(bookStatus, $event)" @dragenter.prevent @dragover.prevent>
    <h1>{{ bookStatus }}</h1>
    <template v-for="book in getBookList(bookStatus)">
      <card-item
        :key="book.id"
        :book="book"
        class="drag-card"
        draggable="true"
        @startDrag="startDrag"
      />
    </template>
  </div>
</template>

<script lang="ts">
  import { Vue, Component, Prop } from 'vue-property-decorator';
  import {
    Book,
    BookReadingStatusType,
    DataTransferType,
  } from '@/components/dragAndDrop/Board.vue';
  import CardItem from '@/components/dragAndDrop/CardItem.vue';
  @Component({
    components: {
      CardItem,
    },
  })
  export default class Card extends Vue {
    @Prop()
    private readonly bookStatus!: BookReadingStatusType;
    @Prop()
    private readonly bookList!: Book[];
    private onDrop(
      bookStatus: BookReadingStatusType,
      { dataTransfer }: DataTransferType
    ) {
      this.$emit('onDrop', { bookStatus, dataTransfer });
    }

    private getBookList(bookStatus: BookReadingStatusType): Book[] {
      return [...this.bookList].filter(book => book.status === bookStatus);
    }

    private startDrag(data: { book: Book; dataTransfer: DataTransfer }) {
      this.$emit('startDrag', data);
    }
  }
</script>

<style scoped></style>
