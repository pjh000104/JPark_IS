"use client";

type Review = {
  id: number;
  rating: number;
  comment: string;
  createdAt: string;
  authorName?: string;
};

type Props = {
  reviews: Review[];
};

export default function ReviewList({ reviews }: Props) {
  if (reviews.length === 0) {
    return <p className="text-gray-500">No reviews yet.</p>;
  }

  return (
    <div className="space-y-4">
      {reviews.map((review) => (
        <div
          key={review.id}
          className="bg-white p-4 rounded-lg shadow border"
        >
          <div className="flex justify-between items-center mb-2">
            <span className="font-semibold text-gray-800">
              {review.authorName || "Anonymous"}
            </span>
            <span className="text-yellow-500 font-bold">{review.rating}⭐</span>
          </div>
          <p className="text-gray-700">{review.comment}</p>
          <p className="text-gray-400 text-sm mt-2">
            {new Date(review.createdAt).toLocaleDateString()}
          </p>
        </div>
      ))}
    </div>
  );
}