<template>
  <div>
    <content-header />
    <div class="board">
      <tags />
      <!--      <board :book-list.sync="bookList" book-status="BOOKING" />-->
    </div>
    <body-content />
    <footer id="footer"></footer>
  </div>
</template>

<script lang="ts">
  import { Vue, Component } from 'vue-property-decorator';
  import ContentHeader from '@/views/main/header/TopContent.vue';
  import BodyContent from '@/views/main/body/BodyContent.vue';
  import Board, { Book } from '@/components/dragAndDrop/Board.vue';
  import Tags from '@/components/tags/Tags.vue';

  const bookList: Book[] = [
    {
      id: 0,
      accountId: 1,
      isbn: 'aaa',
      access: 'PUBLIC',
      status: 'BOOKING',
      review: '',
      category: '',
      score: 0,
      reviewed: false,
    },
    {
      id: 1,
      accountId: 2,
      isbn: 'bbb',
      access: 'PUBLIC',
      status: 'BOOKING',
      review: '',
      category: '',
      score: 0,
      reviewed: false,
    },
    {
      id: 3,
      accountId: 3,
      isbn: 'ccc',
      access: 'PUBLIC',
      status: 'READING',
      review: '',
      category: '',
      score: 0,
      reviewed: false,
    },
    {
      id: 5,
      accountId: 5,
      isbn: 'ddd',
      access: 'PUBLIC',
      status: 'BOOKING',
      review: '',
      category: '',
      score: 0,
      reviewed: false,
    },
    {
      id: 7,
      accountId: 7,
      isbn: 'eee',
      access: 'PRIVATE',
      status: 'READING',
      review: '',
      category: '',
      score: 0,
      reviewed: false,
    },
    {
      id: 8,
      accountId: 8,
      isbn: 'fff',
      access: 'PUBLIC',
      status: 'COMPLETE',
      review: '',
      category: '',
      score: 0,
      reviewed: false,
    },
    {
      id: 10,
      accountId: 10,
      isbn: 'ggg',
      access: 'PRIVATE',
      status: 'BOOKING',
      review: '',
      category: '',
      score: 0,
      reviewed: false,
    },
    {
      id: 11,
      accountId: 11,
      isbn: 'hhh',
      access: 'PUBLIC',
      status: 'COMPLETE',
      review: '',
      category: '',
      score: 0,
      reviewed: false,
    },
    {
      id: 17,
      accountId: 15,
      isbn: 'iii',
      access: 'PUBLIC',
      status: 'BOOKING',
      review: '',
      category: '',
      score: 0,
      reviewed: false,
    },
    {
      id: 20,
      accountId: 21,
      isbn: 'jjj',
      access: 'PRIVATE',
      status: 'COMPLETE',
      review: '',
      category: '',
      score: 0,
      reviewed: false,
    },
  ];

  @Component({
    components: { Tags, Board, BodyContent, ContentHeader },
  })
  export default class App extends Vue {
    private bookList = [...bookList];
  }
</script>
